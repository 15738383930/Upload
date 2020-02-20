package com.example.upload.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * 公共工具接口
 * @author zhouhao
 *
 */
public interface CommUtil {

	/**
	 * 基本属性常量
	 * @author zhouhao
	 *
	 */
	class Property {
		
		/**
		 * 错误请求的URL
		 */
		public final static String ERROR_PATH = "/error.html";

		/**
		 * 人物类型-最大值
		 */
		public final static int CHARACTER_TYPE_MAX = 10;

		/**
		 * 年龄-最大值（默认）
		 */
		public final static int AGE_MAX_DEFAULT = 120;

		/**
		 * 性别-男
		 */
		public final static String SEX_MAN = "男";

		/**
		 * 性别-女
		 */
		public final static String SEX_WOMAN = "女";

		/**
		 * 性别-未知
		 */
		public final static String SEX_UNKNOWN = "未知";

		/**
		 * 项目配置文件
		 */
		public final static String PROBJECT_PROPERTIES = "project.properties";

		/**
		 * 上传
		 */
		public final static String FILE_UPLOAD_URL = "FILE_UPLOAD_URL";

		/**
		 * 下载
		 */
		public final static String FILE_DOWNLOAD_URL = "FILE_DOWNLOAD_URL";

		/**
		 * 保存
		 */
		public final static String FILE_SAVE_URL = "FILE_SAVE_URL";

		/**
		 * 图片访问前缀（项目根路径）
		 */
		public final static String IMAGE_PREFIX = "image-prefix";

		/**
		 * 日期格式化（yyyy-MM-dd HH:mm:ss）
		 */
		public final static String DATE_FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";

		/**
		 * 日期格式化（yyyy-MM-dd） 【默认】
		 */
		public final static String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";

		/**
		 * 日期格式化（yyyy-MM）
		 */
		public final static String DATE_FORMAT_MONTH = "yyyy-MM";

		/**
		 * 日期格式化（yyyy）
		 */
		public final static String DATE_FORMAT_YEAR = "yyyy";

		/**
		 * 返回消息-查询-查询失败
		 */
		public final static String RESULT_QUERY_ERROR_MSG = "查询失败，请刷新后再试。";

		/**
		 * 返回消息-编辑-成功
		 */
		public final static String RESULT_EDIT_SUCCESS_MSG = "操作成功";
		/**
		 * 返回消息-编辑-失败
		 */
		public final static String RESULT_EDIT_ERROR_MSG = "操作失败，请稍后再试。";

		/**
		 * HashMap默认值大小 （参考阿里开发规范）
		 */
		public final static int HASH_MAP_DEFAULT_SIZE = 16;

		/**
		 * 未删除
		 */
		public final static int NOT_DEL = 0;

		/**
		 * 已删除
		 */
		public final static int DEL_ED = 1;

		/**
		 * 一级菜单
		 */
		public final static int LAYER_ONE = 1;

		/**
		 * 二级菜单
		 */
		public final static int LAYER_TWO = 2;

		/**
		 * 数组长度-2
		 */
		public final static int ARRAY_LENGTH_2 = 2;

		/**
		 * 永久删除
		 */
		public final static int DEL_FOREVER = 2;

		public final static String DATA = "data";
		
	}

	/**
	 * 基本公共方法
	 * @author zhouhao
	 *
	 */
	class Method {

		/**
		 * 获取配置文件的value
		 * @param fileName 文件名（例：project.properties）
		 * @param key 对象的key
		 * @return
		 */
	    public static String getPropertiesByKey(String fileName, String key) {
	        Properties prop = new Properties();
	        String value = "";
	        try {
	            InputStream in = CommUtil.Method.class.getClassLoader().getResourceAsStream(fileName);
	            prop.load(in);
	            value = prop.getProperty(key);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return value;
	    }

		/**
		 * 上传图片
		 * @param image
		 * @return
		 * @throws IOException
		 */
	    public static String uploadImage(MultipartFile image) throws IOException {
			// 新图片名称(随机数加上扩展名)
			String newFileName = null;
			if(image != null) {
				// 图片的原始名称
				String originalFilename = image.getOriginalFilename();
				if (originalFilename != null && originalFilename.length() > 0) {
					// 每月创建图片文件夹
					final String dateStr = DateUtil.getDateStr(new Date(), Property.DATE_FORMAT_MONTH);
					// 储存图片的物理路径
					String picPath = CommUtil.Method.getPropertiesByKey(CommUtil.Property.PROBJECT_PROPERTIES, CommUtil.Property.FILE_UPLOAD_URL);
					newFileName = dateStr + "/" + UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
					// 新图片
					File newFile = new File(picPath + dateStr + "/");
					// 创建文件夹
					if(!newFile.exists()){
						newFile.mkdirs();
					}
					// 将内存中的数据写入磁盘
					image.transferTo(new File(picPath + newFileName));

					// 添加图片图片访问前缀
					newFileName = CommUtil.Method.getPropertiesByKey(CommUtil.Property.PROBJECT_PROPERTIES, Property.FILE_DOWNLOAD_URL) + newFileName;
				}
			}
			return newFileName;
		}

		/**
		 * 删除图片文件
		 * @param imgUrl
		 */
		public static void removeImage(String imgUrl) {

			// 处理图片前缀
			final String[] split = imgUrl.split(Method.getPropertiesByKey(Property.PROBJECT_PROPERTIES, Property.IMAGE_PREFIX));
			imgUrl = split.length <= 1 ? imgUrl : split[1];

			if(!StringUtils.isEmpty(imgUrl)) {
				String filePath = CommUtil.Method.getPropertiesByKey(CommUtil.Property.PROBJECT_PROPERTIES, CommUtil.Property.FILE_UPLOAD_URL) + imgUrl;
				File f = new File(filePath);
				if (f.exists()) {
					f.delete();
				}
			}
		}

		/**
		 * 下载文件
		 * @param fileUrl 文件地址
		 * @return
		 * @throws IOException
		 */
		public static boolean downloadFile(String fileUrl) throws IOException {
			URL url = new URL(fileUrl);
			URLConnection conn = url.openConnection();
			InputStream inStream = conn.getInputStream();
			String newFileUrl = CommUtil.Method.getPropertiesByKey(CommUtil.Property.PROBJECT_PROPERTIES, Property.FILE_SAVE_URL);
			String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

			File newFile = new File(newFileUrl);
			// 创建文件夹
			if(!newFile.exists()){
				newFile.mkdirs();
			}

			FileOutputStream fs = new FileOutputStream(newFileUrl + fileName);
			int byteread = 0;
			byte[] buffer = new byte[1204];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
			fs.close();
			inStream.close();
			return new File(newFileUrl + fileName).exists();
		}

		/**
		 * 对BeanUtils.copyProperties复制数据是非空的处理
		 * @param source
		 * @return
		 */
		private static String[] getNullPropertyNames(Object source) {
			final BeanWrapper src = new BeanWrapperImpl(source);
			java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

			Set<String> emptyNames = new HashSet<String>();
			for(java.beans.PropertyDescriptor pd : pds) {
				Object srcValue = src.getPropertyValue(pd.getName());
				if (srcValue == null) {
					emptyNames.add(pd.getName());
				}
			}
			String[] result = new String[emptyNames.size()];
			return emptyNames.toArray(result);
		}

		/**
		 * BeanUtils.copyProperties的非空版本
		 * 			（如果src中有属性为null时，target对应的属性不会被更新）
		 * @param src 我
		 * @param target 目标
		 */
		public static void copyPropertiesIgnoreNull(Object src, Object target){
			BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
		}
	}

	class HttpStatus {
		// ================http状态返回代码 1xx（临时响应）==============================

		/**
		 * （继续）
		 * 请求者应当继续提出请求。 服务器返回此代码表示已收到请求的第一部分，正在等待其余部分。
		 */
		public static final int HTTP_100 = 100;

		/**
		 * （切换协议）
		 * 请求者已要求服务器切换协议，服务器已确认并准备切换。
		 */
		public static final int HTTP_101 = 101;


		// ================http状态返回代码 2xx （成功）==============================

		/**
		 * （成功）
		 * 服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
		 */
		public static final int HTTP_200 = 200;

		/**
		 * （已创建）
		 * 请求成功并且服务器创建了新的资源。
		 */
		public static final int HTTP_201 = 201;

		/**
		 * （已接受）
		 * 服务器已接受请求，但尚未处理。
		 */
		public static final int HTTP_202 = 202;

		/**
		 * （非授权信息）
		 * 服务器已成功处理了请求，但返回的信息可能来自另一来源。
		 */
		public static final int HTTP_203 = 203;

		/**
		 * （无内容）
		 * 服务器成功处理了请求，但没有返回任何内容。
		 */
		public static final int HTTP_204 = 204;

		/**
		 * （重置内容）
		 * 服务器成功处理了请求，但没有返回任何内容。
		 */
		public static final int HTTP_205 = 205;

		/**
		 * （部分内容）
		 * 服务器成功处理了部分 GET 请求。
		 */
		public static final int HTTP_206 = 206;


		// ================http状态返回代码 3xx （重定向）==============================

		/**
		 * （多种选择）
		 * 针对请求，服务器可执行多种操作。 服务器可根据请求者 (user agent) 选择一项操作，或提供操作列表供请求者选择。
		 */
		public static final int HTTP_300 = 300;

		/**
		 * （永久移动）
		 * 请求的网页已永久移动到新位置。 服务器返回此响应（对 GET 或 HEAD 请求的响应）时，会自动将请求者转到新位置。
		 */
		public static final int HTTP_301 = 301;

		/**
		 * （临时移动）
		 * 服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。
		 */
		public static final int HTTP_302 = 302;

		/**
		 * （查看其他位置）
		 * 请求者应当对不同的位置使用单独的 GET 请求来检索响应时，服务器返回此代码。
		 */
		public static final int HTTP_303 = 303;

		/**
		 * （未修改）
		 * 自从上次请求后，请求的网页未修改过。 服务器返回此响应时，不会返回网页内容。
		 */
		public static final int HTTP_304 = 304;

		/**
		 * （使用代理）
		 * 请求者只能使用代理访问请求的网页。 如果服务器返回此响应，还表示请求者应使用代理。
		 */
		public static final int HTTP_305 = 305;

		/**
		 * （临时重定向）
		 * 服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。
		 */
		public static final int HTTP_306 = 306;


		// ================http状态返回代码 4xx（请求错误）==============================

		/**
		 * （错误请求）
		 * 服务器不理解请求的语法。
		 */
		public static final int HTTP_400 = 400;

		/**
		 * （未授权）
		 * 请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。
		 */
		public static final int HTTP_401 = 401;

		/**
		 * （禁止）
		 * 服务器拒绝请求。
		 */
		public static final int HTTP_403 = 403;

		/**
		 * （未找到）
		 * 服务器找不到请求的网页。
		 */
		public static final int HTTP_404 = 404;

		/**
		 * （方法禁用）
		 * 禁用请求中指定的方法。
		 */
		public static final int HTTP_405 = 405;

		/**
		 * （不接受）
		 * 无法使用请求的内容特性响应请求的网页。
		 */
		public static final int HTTP_406 = 406;

		/**
		 * （需要代理授权）
		 * 此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。
		 */
		public static final int HTTP_407 = 407;

		/**
		 * （请求超时）
		 * 服务器等候请求时发生超时。
		 */
		public static final int HTTP_408 = 408;

		/**
		 * （冲突）
		 * 服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息。
		 */
		public static final int HTTP_409 = 409;

		/**
		 * （已删除）
		 * 如果请求的资源已永久删除，服务器就会返回此响应。
		 */
		public static final int HTTP_410 = 410;

		/**
		 * （需要有效长度）
		 * 服务器不接受不含有效内容长度标头字段的请求。
		 */
		public static final int HTTP_411 = 411;


		// ================http状态返回代码  5xx（服务器错误）==============================

		/**
		 * （服务器内部错误）
		 * 服务器遇到错误，无法完成请求。
		 */
		public static final int HTTP_500 = 500;

		/**
		 * （服务不可用）
		 * 服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。
		 */
		public static final int HTTP_503 = 503;

		/**
		 * （网关超时）
		 * 服务器作为网关或代理，但是没有及时从上游服务器收到请求。
		 */
		public static final int HTTP_504 = 504;


		// ================http状态返回代码  6xx（自定义错误）==============================

		/**
		 * （操作成功）
		 */
		public static final int HTTP_C_1 = 1;

		/**
		 * （操作失败）
		 */
		public static final int HTTP_C_0 = 0;

	}

}
