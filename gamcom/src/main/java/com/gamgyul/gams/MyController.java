package com.gamgyul.gams;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.gamgyul.gams.dao.BoardDAOImple;
import com.gamgyul.gams.dao.CmtDAOImple;
import com.gamgyul.gams.dao.CustomerDAOImple;
import com.gamgyul.gams.dto.BoardDTO;
import com.gamgyul.gams.dto.CommentDTO;
import com.gamgyul.gams.dto.CustomerDTO;
import com.gamgyul.gams.util.GamgyulDataSet;
import com.gamgyul.gams.util.NaverUserData;

@Controller
public class MyController {

	@Inject
	BoardDAOImple bbsService;
	
	@Inject
	CustomerDAOImple customerService;
	
	@Inject
	CmtDAOImple cmtService;

	@RequestMapping("/")
	public String Index(Model model, HttpServletRequest req) {
		return "index";
	}

	@RequestMapping("login")
	public String Login(HttpServletRequest req, HttpServletResponse res) {
		String backPage = req.getHeader("referer");
		
		Cookie ckBackPage = new Cookie("backPage", backPage);
		res.addCookie(ckBackPage);
		
		return "login";
	}

	@RequestMapping("board/list")
	public String BBS(Model model, HttpServletRequest req) {
		List<BoardDTO> bbs_list = null;
		String category = req.getParameter("category");
		String title = "";
		
		if(req.getParameter("page") != null) {
			int page = Integer.parseInt(req.getParameter("page"));
			bbs_list = bbsService.getList(page,category);
		}else {
			bbs_list = bbsService.getList(1,category);
		}
		List<Integer> CmtCnt = new ArrayList<Integer>();
		for (int i = 0; i < bbs_list.size(); i++) {
			CmtCnt.add(bbsService.getCmtCnt(bbs_list.get(i).getBoard_idx()));
		}
		
		model.addAttribute("tab", category);
		model.addAttribute("title", title);
		model.addAttribute("bbs_list", bbs_list);
		model.addAttribute("CmtCnt", CmtCnt);

		return "board";
	}

	@RequestMapping("board/view/{board_idx}")
	public String View(@PathVariable("board_idx") final int board_idx, Model model, HttpServletRequest req) {
		bbsService.postHit(board_idx);
		BoardDTO bbs = bbsService.getPost(board_idx);

		List<CommentDTO> cmtList = (List<CommentDTO>)cmtService.getCmt(board_idx);
		
		model.addAttribute("cmtList",cmtList);
		if(bbs != null)
			model.addAttribute("board", bbs);
		else
			model.addAttribute("board", null);
		return "view";
	}
	
	@RequestMapping("board/write")
	public String Write(Model model, HttpServletRequest req) {

		return "write";
	}
	
	@RequestMapping("board/view/{board_idx}/delete")
	public void DelPost(@PathVariable("board_idx") final int board_idx,HttpServletRequest req, HttpServletResponse res, Model model) throws IOException {
		HttpSession session = req.getSession();
		
		CustomerDTO customer = (CustomerDTO)session.getAttribute("customer");
		int result = bbsService.delPost(board_idx, customer.getCustomer_idx(),customer.getCustomer_admin());
		
		String msg = (result==1?"삭제되었습니다.":"삭제 실패");
		String url = req.getContextPath()+"/board/list?category=";
		String category = req.getParameter("category");
		
		FileDelete(board_idx);

		
		res.sendRedirect(req.getContextPath()+"/redirect?msg="+URLEncoder.encode(msg,"UTF-8")+"&url="+url+"&category="+URLEncoder.encode(category,"UTF-8"));
	}
	
	private void FileDelete(int board_idx) {
		final File file = new File(GamgyulDataSet.getRealUploadPath() + board_idx);
		if (file.isDirectory()) {
			final File[] fileChild = file.listFiles();
			new Thread() {
				public void run() {
					try {
						Thread.sleep(1000);
						for (int i = 0; i < fileChild.length; i++) {
							fileChild[i].delete();
						}
						file.delete();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
		}else {
			System.out.println("업로드 된 파일이 없어 삭제를 중단합니다.");
		}
	}
	
	@RequestMapping("redirect")
	public String DelPost(HttpServletRequest req, Model model) throws UnsupportedEncodingException {	
		model.addAttribute("msg",req.getParameter("msg"));
		model.addAttribute("url",req.getParameter("url"));
		model.addAttribute("category",req.getParameter("category"));
		return "redirect";
	}
	
	List<Map<String,Object>> imgTempPaths = new ArrayList<Map<String, Object>>();
	@RequestMapping("/board/upload")
	public void PostUpload(Model model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println(">>>>>>>>>> 글 작성하기");
		String category = req.getParameter("category");
		HttpSession session = req.getSession();
		CustomerDTO customer = (CustomerDTO)session.getAttribute("customer");
		
		String board_subject = "";
		String board_content = "";
		
		board_subject = req.getParameter("subject");
		board_content = req.getParameter("writeContent");
		int NextAI = bbsService.getNextId();
		for(int i = 0; i < imgTempPaths.size();i++) {
			Map<String, Object> tempMap = new HashMap<String, Object>();
			
			tempMap = (Map<String, Object>)imgTempPaths.get(i);
			String fileName = tempMap.get("fileName").toString();
			File path = new File(tempMap.get("newFile").toString()+NextAI); // 경로를 생성하기 위한 파일
			File oldFile = new File(tempMap.get("oldFile").toString()+fileName);	// 기존 이미지 파일의 경로
	        File newFile = new File(tempMap.get("newFile").toString()+NextAI+"/"+fileName);	// 이미지 파일을 옮길 경로
	        String temp = tempMap.get("temp").toString(); // 디비에 올라갈 경로를 치환하기 위해 저장한 문자열

	        board_content = board_content.replace(temp, "/upload/"+NextAI+"/"+fileName);
	        genPathAndDel(oldFile, newFile, path);
		}
		
		
		
		
		board_subject = board_subject.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		
		
		BoardDTO bds = new BoardDTO(0, board_subject, board_content, customer.getCustomer_idx(), customer.getCustomer_name(), new Date(), 0, 0,category);
		bbsService.writePost(bds, category);
		
		imgTempPaths.clear();
		
		res.sendRedirect(req.getContextPath() + "/board/list?category="+URLEncoder.encode(category,"UTF-8"));
		
	}
	
	@RequestMapping("/board/imageUpload")
	public void BoardImageUpload(HttpServletRequest req, HttpServletResponse res, @RequestParam MultipartFile upload) throws IOException, Exception {	
		OutputStream out = null;
        PrintWriter printWriter = null;
        res.setCharacterEncoding("utf-8");
        res.setContentType("text/html;charset=utf-8");
 
        try{
 
        	String fileName = new Date().getTime()+"_"+upload.getOriginalFilename();
            byte[] bytes = upload.getBytes();
            String onlyPath = req.getSession().getServletContext().getRealPath("/resources/images/imgTemp/");
            String uploadPath = onlyPath + fileName;//저장경로

            out = new FileOutputStream(new File(uploadPath));
            out.write(bytes);
            
            String callback = req.getParameter("CKEditorFuncNum");
            printWriter = res.getWriter();
            
            String tempPath = onlyPath+fileName;
            
            
            
            Map<String, Object> tempMap = new HashMap<String, Object>();
            tempMap.put("oldFile", onlyPath);
            tempMap.put("newFile", GamgyulDataSet.getRealUploadPath());
            tempMap.put("temp", tempPath);
            tempMap.put("fileName", fileName);

            imgTempPaths.add(tempMap);
 
            System.out.println(GamgyulDataSet.getRealUploadPath());
            
            
            printWriter.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
                    + callback
                    + ",'"
                    + tempPath
                    + "','이미지를 업로드 하였습니다.'"
                    + ")</script>");
            
            
            printWriter.flush();
 
        }catch(IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        return;
	}
	
	@ResponseBody
	@RequestMapping(value = "/board/imageUpload/drag", produces="application/json;charset=UTF-8")
	public Object BoardImageUploadDrag(HttpServletRequest req, HttpServletResponse res, @RequestParam MultipartFile upload) throws IOException, Exception {
		OutputStream out = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
        res.setCharacterEncoding("utf-8");
        res.setContentType("text/html;charset=utf-8");
 
        try{
 
            String fileName = new Date().getTime()+"_"+upload.getOriginalFilename();
            byte[] bytes = upload.getBytes();
            
            
            //진짜 업로드 위치
            String onlyPath = req.getSession().getServletContext().getRealPath("/resources/images/imgTemp/");
            
            
            
            String uploadPath = onlyPath + fileName;//저장경로
            out = new FileOutputStream(new File(uploadPath));
            out.write(bytes);
            
            
            //진짜 미리보기 위치
            //String tempPath = req.getContextPath()+GamgyulDataSet.getImgTempPath()+fileName;
            //집에서만
            String tempPath = "/uploadimage/"+fileName;
            System.out.println(tempPath);
            
            map.put("uploaded", 1);
            map.put("url", tempPath);
            map.put("fileName", fileName);

            
            
            Map<String, Object> tempMap = new HashMap<String, Object>();
            tempMap.put("oldFile", onlyPath);
            tempMap.put("newFile", GamgyulDataSet.getRealUploadPath());
            tempMap.put("temp", tempPath);
            tempMap.put("fileName", fileName);

            imgTempPaths.add(tempMap);
            
            
            
            ObjectMapper mapper = new ObjectMapper();
            String json;
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
            
            return json;
        }catch(IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        return null;
	}
	
	
	@Scheduled(cron="0 * * * * *")
	public void DelTempImg() {
		Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("스케줄 실행 : " + dateFormat.format(calendar.getTime()));
	}
	
	
	private void genPathAndDel(final File oldFile, File newFile, File temp) {
		 if(!temp.exists()) {
         	temp.mkdirs();
         }
         if(oldFile.renameTo(newFile)==false) {
         	copyUploadPath(oldFile, newFile);
         	
         	new Thread() {
         		public void run() {
         			try {
							Thread.sleep(1000);
							oldFile.delete();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
         		}
         	}.start();
         }
	}
	
	private void copyUploadPath(File input, File output) {
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(input));
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(output));
			
			int temp;
			
			while((temp=in.read()) != -1) {
				out.write(temp);
			}
			
			in.close();
			out.flush();
			out.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	@RequestMapping("/naver/naverlogin")
	public String naverLogin(HttpSession session) {
		return "/naver/naverlogin";
	}

	@RequestMapping("/naver/callback")
	public void naverLoginCallback(HttpServletRequest req, HttpServletResponse res) throws Exception {
		naverLoginSuccess(req, res);
	}
	

	
	
	public void naverLoginSuccess(HttpServletRequest req, HttpServletResponse res) throws Exception { 
		HttpSession session = req.getSession();
		
		String clientId = "96fum_n52OUBuW6IDWP2";//애플리케이션 클라이언트 아이디값";
	    String clientSecret = "Gmq54ctgix";//애플리케이션 클라이언트 시크릿값";
	    String code = req.getParameter("code");
	    String state = req.getParameter("state");
	    String redirectURI = URLEncoder.encode(GamgyulDataSet.getIp()+"naver/callback", "UTF-8");
	    String apiURL;
	    apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
	    apiURL += "client_id=" + clientId;
	    apiURL += "&client_secret=" + clientSecret;
	    apiURL += "&redirect_uri=" + redirectURI;
	    apiURL += "&code=" + code;
	    apiURL += "&state=" + state;
	    String access_token = "";
	    String refresh_token = "";
	    try {
	      URL url = new URL(apiURL);
	      HttpURLConnection con = (HttpURLConnection)url.openConnection();
	      con.setRequestMethod("GET");
	      int responseCode = con.getResponseCode();
	      BufferedReader br;
	      System.out.print("responseCode="+responseCode);
	      if(responseCode==200) { // 정상 호출
	        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	      } else {  // 에러 발생
	        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	      }
	      String inputLine;
	      StringBuffer sb = new StringBuffer();
	      while ((inputLine = br.readLine()) != null) {
	        sb.append(inputLine);
	      }
	      br.close();
	      if(responseCode==200) {
	        //out.println(sb.toString());
	       	ObjectMapper mapper = new ObjectMapper();
	       	Map<String, Object> map_origin = new HashMap<String, Object>();
	       	map_origin = mapper.readValue(sb.toString(), new TypeReference<Map<String, String>>(){});

	       	access_token = map_origin.get("access_token").toString();    	
	       	String user_json = NaverUserData.getUserData(access_token);       	
	       	Map <String, Object> map_user = new ObjectMapper().readValue(user_json, Map.class);
	        String json = map_user.get("response").toString();

	       	json = json.replace("{", "");
	       	json = json.replace("}", "");
	       	String jsons[] = json.split(",");
	      	
	       	
	       	CustomerDTO customer = new CustomerDTO();
	       	customer.setCustomer_idx(jsons[0].substring(jsons[0].indexOf("=")+1).toString());
	       	customer.setCustomer_name(jsons[1].substring(jsons[1].indexOf("=")+1).toString());
	       	customer.setCustomer_url(jsons[2].substring(jsons[2].indexOf("=")+1).toString());
	       	customer.setCustomer_email(jsons[3].substring(jsons[3].indexOf("=")+1).toString());
	       	customer.setCustomer_pb("naver");
	       	customer.setCustomer_admin((customer.getCustomer_email().equals("zanghwkddnwl@naver.com")?1:0));
	       	
	       
	       	
	       	if(customerService.getCustCnt(customer.getCustomer_idx(), customer.getCustomer_pb())==0) {
				System.out.println(">>>>>>>>>>	DB에 유저 정보 없음\t|\tNAVER");
				System.out.println(">>>>>>>>>>	DB정보 입력하는중\t|\tNAVER");
				customerService.addCust(customer);
				System.out.println(">>>>>>>>>>	DB정보 입력완료\t|\tNAVER");
			}
	       	
	    	System.out.println(customer.toString());
	       	session.removeAttribute("customer");
	       	session.setAttribute("customer", customer);
	       	
	      }
	    } catch (Exception e) {
	      System.out.println(e);
	    }
	    
	    Cookie[] cookies = req.getCookies();
	    String backPage = "";
	    
	    for(int i = 0; i < cookies.length; i++) {
	    	if(cookies[i].getName().equals("backPage")) {
	    		backPage = cookies[i].getValue();
	    		cookies[i].setMaxAge(0);
	    	    res.addCookie(cookies[i]);
	    	}
	    }
	    

	    System.out.println("로그인 후 갈 페이지"+backPage);
	    res.sendRedirect(backPage);
	}

	@RequestMapping("/google/googlelogin")
	public String GoogleLogin(Model model, HttpSession session) {

		return "/google/googlelogin";
	}

	@RequestMapping("/google/callback")
	public void googleLoginCallback(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String code = req.getParameter("code");
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.add("code", code);
		parameters.add("client_id", GamgyulDataSet.getClientId());
		parameters.add("client_secret", GamgyulDataSet.getSecretId());
		parameters.add("redirect_uri", GamgyulDataSet.getIp() + "google/callback");
		parameters.add("grant_type", "authorization_code");

		HttpEntity<MultiValueMap<String, String>> rest_request = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);

		URI uri = URI.create("https://www.googleapis.com/oauth2/v4/token");

		ResponseEntity<String> rest_reponse;
		rest_reponse = restTemplate.postForEntity(uri, rest_request, String.class);
		String bodys = rest_reponse.getBody();
		// System.out.println(bodys);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
		map = mapper.readValue(bodys, new TypeReference<Map<String, String>>() {	});

		String profile = map.get("id_token");

		googleTokenControll(profile, req, res);

	}

	private void googleTokenControll(String token, HttpServletRequest req, HttpServletResponse res) throws Exception {

		Decoder decoder = Base64.getUrlDecoder();
		String[] parts = token.split("\\.");

		String infor = new String(decoder.decode(parts[1]));

		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
		map = mapper.readValue(infor, new TypeReference<Map<String, String>>() {
		});

		CustomerDTO customer = new CustomerDTO();
		customer.setCustomer_idx(map.get("sub"));
		customer.setCustomer_name(map.get("name"));
		customer.setCustomer_url(map.get("picture"));
		customer.setCustomer_email(map.get("email"));
		customer.setCustomer_pb("google");
		customer.setCustomer_admin((customer.getCustomer_email().equals("gamgyule@gmail.com")?1:0));

		System.out.println("Login > "+customer.toString());

		System.out.println(customer.toString());
		
		if(customerService.getCustCnt(customer.getCustomer_idx(), customer.getCustomer_pb())==0) {
			System.out.println(">>>>>>>>>>	DB에 유저 정보 없음\t|\tGOOGLE");
			System.out.println(">>>>>>>>>>	DB정보 입력하는중 \t|\tGOOGLE");
			customerService.addCust(customer);
			System.out.println(">>>>>>>>>>	DB정보 입력완료 \t|\tGOOGLE");
		}
		
		HttpSession session = req.getSession();
		session.removeAttribute("customer");
		session.setAttribute("customer", customer);
		
		Cookie[] cookies = req.getCookies();
	    String backPage = "";
	    
	    for(int i = 0; i < cookies.length; i++) {
	    	if(cookies[i].getName().equals("backPage")) {
	    		backPage = cookies[i].getValue();
	    		cookies[i].setMaxAge(0);
	    	    res.addCookie(cookies[i]);
	    	}
	    }
	    

	    System.out.println(backPage);
	    res.sendRedirect(backPage);
	}

	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		return "logout";
	}

	// 폼 액션 전용

	// AJAX
	@RequestMapping("/ajax/board/addvote")
	@ResponseBody
	public void addVote(HttpServletRequest req) {
		String board_idx = req.getParameter("board_idx");

		bbsService.addVote(board_idx);
	}
	
	@RequestMapping("/ajax/board/getvote")
	@ResponseBody
	public String getVote(HttpServletRequest req) {
		String board_idx = req.getParameter("board_idx");


		String result = bbsService.getVote(board_idx);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/board/view/{board_idx}/comment/upload")
	public void commentUpload(@PathVariable("board_idx") final int board_idx, HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		
		String content = req.getParameter("content");
		CustomerDTO customer = (CustomerDTO)session.getAttribute("customer");
		CommentDTO cmt = new CommentDTO(0,board_idx,customer.getCustomer_idx(),customer.getCustomer_name(),customer.getCustomer_url(),content,new Date(),0);
		
		cmtService.addCmt(cmt);
		
	}
	
	@RequestMapping(value="/ajax/board/{board_idx}/getCmt",produces = "application/text; charset=utf8")
	@ResponseBody
	public String getCmt(@PathVariable("board_idx") final int board_idx, HttpServletRequest req, HttpServletResponse res) throws ParseException {
		List<CommentDTO> cmtList = (List<CommentDTO>)cmtService.getCmt(board_idx);
		String bcustomer_idx = req.getParameter("bcustomer_idx");
		
		/*<div class='cmt_writer'>
		<div class='cmt_header'><span><%=cmt.getCustomer_name() %></span><img class='cmt_customer_profile' src='<%=cmt.getCustomer_url()%>'></div>
		<div class='cmt_main'><span><%=cmt.getCmt_contents() %></span></div>
		</div>*/
		
		/*<div class='cmt_other'>
		<div class='cmt_header'><img class='cmt_customer_profile' src='<%=cmt.getCustomer_url()%>'><span><%=cmt.getCustomer_name() %></span></div>
		<div class='cmt_main'><span><%=cmt.getCmt_contents() %></span></div>
		</div>*/
		
		String str = "";
		for(int i=0;i<cmtList.size();i++) {
			String[] strr = cmtList.get(i).toString().split("!@##@!");
			if(bcustomer_idx.equals(strr[2])) {
				str += "<div class='cmt_writer'>" + 
						"		<div class='cmt_header'><span>"+strr[3]+"</span><img class='cmt_customer_profile' src='"+strr[4]+"'></div>" + 
						"		<div class='cmt_main'><span>"+strr[5]+"</span></div>" + strr[6] +
						"		</div>";
			}else {
				str += "<div class='cmt_other'>" +
						"		<div class='cmt_header'><img class='cmt_customer_profile' src='"+strr[4]+"'><span>"+strr[3]+"</span></div>" + 
						"		<div class='cmt_main'><span>"+strr[5]+"</span></div>" + strr[6] +
						"		</div>";
			}
		}
		return str;
	}
	
	

}
