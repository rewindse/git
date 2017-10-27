package vm.util;

import java.io.File;

import vm.db.common.Configurations;

public interface Const {
	
	public static final String APP_CONTEXT_PATH = Configurations.getInstance().getProperty("path.context");

	public static final String RESULT_FILE_DIR = APP_CONTEXT_PATH + File.separator + "store" + File.separator + "result";
	
	public static final String SOURCE_FILE_DIR = APP_CONTEXT_PATH + File.separator + "store" + File.separator + "source";
	
	public static final String POSTER_FILE_DIR = APP_CONTEXT_PATH + File.separator + "store" + File.separator + "poster";
	
	public static final String UNCOMPRESS_TEMP_DIR = APP_CONTEXT_PATH + File.separator + "store" + File.separator + "uncompress_temp_dir";
	
	public static final String DELETE_FILE_DIR = APP_CONTEXT_PATH + File.separator + "store" + File.separator +"source" + File.separator + "delete";
	
	public static final String ORIGINALFILE_NOTE_SEP = ";&";
	
	public static final String RESULT_FILE_NOTE_SEP = ORIGINALFILE_NOTE_SEP;
	
}
