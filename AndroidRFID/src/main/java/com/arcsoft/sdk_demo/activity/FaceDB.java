package com.arcsoft.sdk_demo.activity;

import android.os.Environment;
import android.util.Log;

import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKVersion;
import com.arcsoft.sdk_demo.http.AndroidUploadFile;
import com.arcsoft.sdk_demo.set.setdata;
import com.guo.android_extend.java.ExtInputStream;
import com.guo.android_extend.java.ExtOutputStream;

import org.apache.http.client.ClientProtocolException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gqj3375 on 2017/7/11.
 */

public class FaceDB {
	private final String TAG = this.getClass().toString();

	public static String appid = "7tvrXRqr1WcHERC9V6eb3GByVGKdpqhrunkgNrWR2vgs";
	public static String ft_key = "BjRHub79DHCS6yCbyEVR1dRv1hZ4w5XLUCvZ17iRkZx6";
	public static String fd_key = "BjRHub79DHCS6yCbyEVR1dS3B6pE2A3ruwuHzVeT2fWL";
	public static String fr_key = "BjRHub79DHCS6yCbyEVR1dSXphrsSx9MxmraV1aCYLi8";
	public static String age_key = "BjRHub79DHCS6yCbyEVR1dSn9WPDDcFuNcKAQkRATk2g";
	public static String gender_key = "BjRHub79DHCS6yCbyEVR1dSuJueSGb5dqnwSnyj4wYsM";

	String mDBPath;
	List<FaceRegist> mRegister;
	AFR_FSDKEngine mFREngine;
	AFR_FSDKVersion mFRVersion;
	boolean mUpgrade;

	class FaceRegist {//注册
		String mName;
		List<AFR_FSDKFace> mFaceList;

		public FaceRegist(String name) {
			mName = name;
			mFaceList = new ArrayList<>();
		}
	}

	public FaceDB(String path) {
		mDBPath = path;
		mRegister = new ArrayList<>();
		mFRVersion = new AFR_FSDKVersion();
		mUpgrade = false;
		mFREngine = new AFR_FSDKEngine();
		AFR_FSDKError error = mFREngine.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
		if (error.getCode() != AFR_FSDKError.MOK) {
			Log.e(TAG, "AFR_FSDK_InitialEngine fail! error code :" + error.getCode());
		} else {
			mFREngine.AFR_FSDK_GetVersion(mFRVersion);
			Log.d(TAG, "AFR_FSDK_GetVersion=" + mFRVersion.toString());
		}
	}

	public void destroy() {
		if (mFREngine != null) {
			mFREngine.AFR_FSDK_UninitialEngine();
		}
	}

	private boolean saveInfo() {
		try {
			FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt");
			ExtOutputStream bos = new ExtOutputStream(fs);
			bos.writeString(mFRVersion.toString() + "," + mFRVersion.getFeatureLevel());
			bos.close();
			fs.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean loadInfo() {
		if (!mRegister.isEmpty()) {
			return false;
		}
		try {
			FileInputStream fs = new FileInputStream(mDBPath + "/face.txt");
			ExtInputStream bos = new ExtInputStream(fs);
			//load version
			String version_saved = bos.readString();
			if (version_saved.equals(mFRVersion.toString() + "," + mFRVersion.getFeatureLevel())) {
				mUpgrade = true;
			}
			//load all regist name.
			if (version_saved != null) {
				for (String name = bos.readString(); name != null; name = bos.readString()){
					if (new File(mDBPath + "/" + name + ".data").exists()) {
						mRegister.add(new FaceRegist(new String(name)));
						Log.d(TAG, "name=" + name);
					}
				}
			}
			bos.close();
			fs.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean loadFaces(){
		if (loadInfo()) {
			try {
				for (FaceRegist face : mRegister) {
					Log.d(TAG, "load name:" + face.mName + "'s face feature data.");
					FileInputStream fs = new FileInputStream(mDBPath + "/" + face.mName + ".data");
					ExtInputStream bos = new ExtInputStream(fs);
					AFR_FSDKFace afr = null;
					do {
						if (afr != null) {
							if (mUpgrade) {
								//upgrade data.
							}
							face.mFaceList.add(afr);
						}
						afr = new AFR_FSDKFace();
					} while (bos.readBytes(afr.getFeatureData()));
					bos.close();
					fs.close();
					Log.d(TAG, "load name: size = " + face.mFaceList.size());
				}
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public	void addFace(String name, AFR_FSDKFace face) {
		try {
			//check if already registered.
			boolean add = true;
			for (FaceRegist frface : mRegister) {
				if (frface.mName.equals(name)) {
					frface.mFaceList.add(face);
					add = false;
					break;
				}
			}
			if (add) { // not registered.
				FaceRegist frface = new FaceRegist(name);
				frface.mFaceList.add(face);
				mRegister.add(frface);
			}

			if (saveInfo()) {
				//update all names
				FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt", true);
				ExtOutputStream bos = new ExtOutputStream(fs);
				for (FaceRegist frface : mRegister) {
					bos.writeString(frface.mName);
				}
				bos.close();
				fs.close();

				//save new feature
				fs = new FileOutputStream(mDBPath + "/" + name + ".data", true);
				bos = new ExtOutputStream(fs);
				bos.writeBytes(face.getFeatureData());
				bos.close();
				fs.close();

				final File file=new File(mDBPath + "/" + name + ".data");
				new Thread(){
					public void run() {
						try {
							AndroidUploadFile.uoloadFile(file.getAbsolutePath(), new setdata().Urls+"face/uploadfile");
						} catch (ClientProtocolException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					};
				}.start();

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean delete(String name) {
		try {
			//check if already registered.
			boolean find = false;
			for (FaceRegist frface : mRegister) {
				if (frface.mName.equals(name)) {
					File delfile = new File(mDBPath + "/" + name + ".data");
					if (delfile.exists()) {
						delfile.delete();
					}
					mRegister.remove(frface);
					find = true;
					break;
				}
			}

			if (find) {
				if (saveInfo()) {
					//update all names
					FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt", true);
					ExtOutputStream bos = new ExtOutputStream(fs);
					for (FaceRegist frface : mRegister) {
						bos.writeString(frface.mName);
					}
					bos.close();
					fs.close();
				}
			}
			return find;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean upgrade() {
		return false;
	}
}
