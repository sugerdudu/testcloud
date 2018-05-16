package com.sixi.oaservice.plugin;

import com.sixi.oaplatform.common.kits.StrKit;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代码生成类
 * Created by Administrator on 2017/1/10.
 * @author Administrator
 */
public class MybatisGeneratorUtil {

	/**
	 * generatorConfig模板路径
	 */
	private static String generatorConfig_vm = "/template/mybatis/GeneratorConfig.vm";
	/**
	 * Service模板路径
	 */
	private static String service_vm = "/template/mybatis/Service.vm";
	/**
	 * ServiceImpl模板路径
	 */
	private static String serviceImpl_vm = "/template/mybatis/ServiceImpl.vm";
	/**
	 * Controller模板路径
	 */
	private static String controller_vm = "/template/mybatis/Controller.vm";

	private static boolean isCreateNewFile = false;

	/**
	 * 根据模板生成generatorConfig.xml文件
	 * @param jdbcDriver   驱动路径
	 * @param jdbcUrl      链接
	 * @param jdbcUsername 帐号
	 * @param jdbcPassword 密码
	 * @param tableName      表名
	 * @param targetProject  目标项目
	 * @param packageName 包名
	 * @param dataSourcePackageName 数据源包名
	 * @param transaction 事务名称
	 */
	public static void generator(
			String jdbcDriver,
			String jdbcUrl,
			String jdbcUsername,
			String jdbcPassword,
			String tableName,
			String targetProject,
			String packageName,
			String dataSourcePackageName,
			String transaction,
			String tableSchema,
			String modelPackage) throws Exception{

		String os = System.getProperty("os.name");
		String osUserName = System.getProperty("user.name");

		if (os.toLowerCase().startsWith("win")) {
			URL resource = MybatisGeneratorUtil.class.getResource(generatorConfig_vm);
			generatorConfig_vm = resource.getPath().replaceFirst("/", "");
			service_vm = MybatisGeneratorUtil.class.getResource(service_vm).getPath().replaceFirst("/", "");
			serviceImpl_vm = MybatisGeneratorUtil.class.getResource(serviceImpl_vm).getPath().replaceFirst("/", "");
			controller_vm = MybatisGeneratorUtil.class.getResource(controller_vm).getPath().replaceFirst("/","");
		} else {
			generatorConfig_vm = MybatisGeneratorUtil.class.getResource(generatorConfig_vm).getPath();
			service_vm = MybatisGeneratorUtil.class.getResource(service_vm).getPath();
			serviceImpl_vm = MybatisGeneratorUtil.class.getResource(serviceImpl_vm).getPath();
			controller_vm = MybatisGeneratorUtil.class.getResource(controller_vm).getPath();
		}

		//String targetProject = module + "/" + module + "-dao";
		String basePath = MybatisGeneratorUtil.class.getResource("/").getPath().replace("/target/test-classes/", "").replace(targetProject, "").replaceFirst("/", "");
		String generatorConfigXml = MybatisGeneratorUtil.class.getResource("/").getPath().replace("/target/test-classes/", "") + "/src/main/resources/GeneratorConfig.xml";
		targetProject = basePath + targetProject;

		System.out.println("========== 开始生成generatorConfig.xml文件 ==========");
		try {
			Map<String,Object> context = new HashMap<>();

			//String targetProjectSqlMap = basePath + module + "/" + module + "-rpc-service";
			context.put("generator_jdbc_driver",jdbcDriver);
			jdbcUrl = jdbcUrl.replaceAll("&","&amp;");
			context.put("generator_jdbc_url",jdbcUrl);
			context.put("generator_jdbc_username",jdbcUsername);
			context.put("generator_jdbc_password", jdbcPassword);

			context.put("targetProject", targetProject);
			context.put("generator_javaModelGenerator_targetPackage", packageName+".domain.model."+dataSourcePackageName);
			context.put("generator_sqlMapGenerator_targetPackage", packageName+".mapper."+dataSourcePackageName);
			context.put("generator_javaClientGenerator_targetPackage", packageName+".mapper."+dataSourcePackageName);

			context.put("table_name",tableName);
			context.put("model_name", StrKit.lineToHump(tableName));
			context.put("table_schema",tableSchema);

			isCreateNewFile = FreeMarkerUtil.generate(generatorConfig_vm, generatorConfigXml, context);
			// 删除旧代码
			deleteDir(new File(targetProject + "/src/main/java/" + packageName.replaceAll("\\.", "/")+"/controller/" + modelPackage.replaceAll("\\.","/") + "/" + StrKit.lineToHump(tableName)+".java") );
			deleteDir(new File(targetProject + "/src/main/java/" + packageName.replaceAll("\\.", "/")+"/domain/model/"+modelPackage.replaceAll("\\.","/") + "/" + StrKit.lineToHump(tableName)+".java") );
			//删除mapper接口代码
			deleteDir(new File(targetProject + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/mapper/" + dataSourcePackageName.replaceAll("\\.", "/") + "/" + StrKit.lineToHump(tableName)+"Mapper.java"));
			//删除mapper xml代码
			deleteDir(new File(targetProject + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/mapper/" + dataSourcePackageName.replaceAll("\\.", "/") + "/" + StrKit.lineToHump(tableName)+"Mapper.xml"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("========== 结束生成generatorConfig.xml文件 ==========");

		System.out.println("========== 开始运行MybatisGenerator ==========");
		if (isCreateNewFile) {
			List<String> warnings = new ArrayList<>();
			File configFile = new File(generatorConfigXml);
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(configFile);
			DefaultShellCallback callback = new DefaultShellCallback(true);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
			for (String warning : warnings) {
				System.out.println(warning);
			}
		}
		System.out.println("========== 结束运行MybatisGenerator ==========");

		System.out.println("========== 开始生成Service ==========");
		String nowtime = new SimpleDateFormat("yyyy/M/d").format(new Date());

		String servicePath = targetProject + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/service/" + modelPackage.replaceAll("\\." , "/");
		String controllerPath = targetProject + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/controller/" + modelPackage.replaceAll("\\.","/") ;

		String model = StrKit.lineToHump(tableName);
		String service = servicePath + "/" + model + "Service.java";
		String serviceImpl = servicePath + "/impl" + "/" + model + "ServiceImpl.java";
		String controller = controllerPath + "/" + model + "Controller.java";
		// 生成service
		File serviceFile = new File(service);
		if (!serviceFile.exists()) {
			Map<String,Object> context = new HashMap<>(5);
			context.put("packageName",packageName);
			context.put("layer",modelPackage);
			context.put("modelname", model);
			context.put("autor",osUserName);
			context.put("nowtime", nowtime);
			context.put("model",model);
			FreeMarkerUtil.generate(service_vm, service, context);
			System.out.println(service);
		}
		// 生成serviceImpl
		File serviceImplFile = new File(serviceImpl);
		if (!serviceImplFile.exists()) {
			Map<String,Object> context = new HashMap<>(8);
			context.put("packageName",packageName);
			context.put("layer", modelPackage);
			context.put("model", model);
			context.put("modelname",model);
			context.put("autor",osUserName);
			context.put("nowtime",nowtime);
			context.put("transaction",transaction);
			context.put("mapper", StrKit.toLowerCaseFirstOne(model));
			context.put("dataSourcePackageName",dataSourcePackageName);
			context.put("schema",tableSchema);
			FreeMarkerUtil.generate(serviceImpl_vm, serviceImpl, context);
			System.out.println(serviceImpl);
		}
		// 生成Controller
		File controllerFile = new File(controller);
		if (!controllerFile.exists()){
			Map<String,Object> context = new HashMap<>(8);
			context.put("packageName",packageName);
			context.put("layer", modelPackage);
			context.put("model",model);
			context.put("modelname",model);
			context.put("autor",osUserName);
			context.put("nowtime",nowtime);
			context.put("mapper",StrKit.toLowerCaseFirstOne(model));
			FreeMarkerUtil.generate(controller_vm,controller,context);
			System.out.println(controller);
		}
		System.out.println("========== 结束生成Service ==========");
	}

	/**
	 * 递归删除非空文件夹
	 *
	 * @param dir
	 */
	private static void deleteDir(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			assert files != null;
			for (File file : files) {
				deleteDir(file);
			}
		}
		dir.delete();
	}

}
