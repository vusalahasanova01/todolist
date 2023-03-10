package com.todolist.todolist.util;

public class HtmlUtil {

    private HtmlUtil() {
    }

    public static final String operationIsSuccessful =
            """
            <!DOCTYPE html>
            <html>
            <head>
            	<meta charset="UTF-8">
            	<title>Success</title>
            	<style>
            		body {
            			font-family: Arial, sans-serif;
            			background-color: #f2f2f2;
            			margin: 0;
            			padding: 0;
            		}
            		
            		.container {
            			max-width: 800px;
            			margin: 0 auto;
            			padding: 20px;
            			background-color: #fff;
            			box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            		}
            		
            		h1 {
            			font-size: 36px;
            			color: #333;
            			margin-top: 0;
            			margin-bottom: 10px;
            		}
            		
            		p {
            			font-size: 18px;
            			color: #666;
            			margin-top: 0;
            			margin-bottom: 20px;
            		}
            	</style>
            </head>
            <body>
            	<div class="container">
            		<h1>Operation is successful.</h1>
            		<p>Please go to the website.</p>
            	</div>
            </body>
            </html>""";

    public static final String somethingIsWrong =
            """
            <!DOCTYPE html>
            <html>
            <head>
            	<meta charset="UTF-8">
            	<title>Success</title>
            	<style>
            		body {
            			font-family: Arial, sans-serif;
            			background-color: #f2f2f2;
            			margin: 0;
            			padding: 0;
            		}
            		
            		.container {
            			max-width: 800px;
            			margin: 0 auto;
            			padding: 20px;
            			background-color: #fff;
            			box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            		}
            		
            		h1 {
            			font-size: 36px;
            			color: #333;
            			margin-top: 0;
            			margin-bottom: 10px;
            		}
            		
            		p {
            			font-size: 18px;
            			color: #666;
            			margin-top: 0;
            			margin-bottom: 20px;
            		}
            	</style>
            </head>
            <body>
            	<div class="container">
            		<h1>Something is wrong!!!.</h1>
            		<p>Please try again.</p>
            	</div>
            </body>
            </html>""";

}
