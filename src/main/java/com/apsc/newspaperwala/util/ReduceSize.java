/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2020 iText Group NV
    Authors: iText Software.
 
    For more information, please contact iText Software at this address:
    sales@itextpdf.com
 */
/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/30483622/compressing-images-in-existing-pdfs-makes-the-resulting-pdf-file-bigger-lowagie
 */
package com.apsc.newspaperwala.util;
 
import com.aspose.pdf.Document;
import com.aspose.pdf.optimization.OptimizationOptions;
 
public class ReduceSize {
    
    public static boolean manipulatePdfUsingAspose(String dest) throws Exception {
    	// Open document
    	Document pdfDocument = new Document(dest+".pdf");

    	OptimizationOptions optimizeOptions = new OptimizationOptions();
    	// Set CompressImages option
    	optimizeOptions.setCompressImages(true);
    	optimizeOptions.setImageQuality(50);
    	pdfDocument.optimizeResources(optimizeOptions);
    	
    	// Save output document
    	pdfDocument.save(dest+".pdf");
    	
    	return true;
    }
}