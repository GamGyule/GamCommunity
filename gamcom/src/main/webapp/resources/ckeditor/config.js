/**
 * @license Copyright (c) 2003-2019, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here.
	// For complete reference see:
	// https://ckeditor.com/docs/ckeditor4/latest/api/CKEDITOR_config.html

	// The toolbar groups arrangement, optimized for two toolbar rows.
	config.toolbar = [
		['Bold','Italic','Underline','Strike'],
		['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
		['Image'],
		['Font','FontSize'],['Maximize', 'ShowBlocks']
		];
	config.font_names = '맑은 고딕; 돋움; 바탕; 돋음; 궁서; Nanum Gothic Coding; Quattrocento Sans;' + CKEDITOR.config.font_names; 
	config.extraPlugins = 'image2';
	config.filebrowserUploadMethod = 'form';
	config.format_tags = 'p;h1;h2;h3;pre';
	config.extraAllowedContent = 'video[*]{*};source[*]{*}';
	// Set the most common block elements.
	
};
