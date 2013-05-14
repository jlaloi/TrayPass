package traypass.syntax.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginLoader<T> {

	private static final Logger logger = LoggerFactory.getLogger(PluginLoader.class);

	private final Class<T> typeParameterClass;

	public PluginLoader(Class<T> typeParameterClass) {
		super();
		this.typeParameterClass = typeParameterClass;
	}

	public List<T> getClassFromPath(String path, boolean recursif) {
		List<T> result = new ArrayList<T>();
		File directory = new File(path);
		if (directory.exists() && directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				if (file.isFile() && file.getName().toLowerCase().endsWith(".jar")) {
					result.addAll(getClassFromJar(file.getAbsolutePath()));
				} else if (file.isDirectory() && recursif) {
					result.addAll(getClassFromPath(file.getAbsolutePath(), recursif));
				}
			}
		}
		return result;
	}

	public List<T> getClassFromJar(String jarFilePath) {
		List<T> result = new ArrayList<T>();
		try {
			logger.info("Plugin loading from: " + jarFilePath );
			JarFile jarFile = new JarFile(jarFilePath);
			Enumeration<JarEntry> e = jarFile.entries();
			URLClassLoader cl = URLClassLoader.newInstance(new URL[] { new URL("jar:file:" + jarFilePath + "!/") });
			while (e.hasMoreElements()) {
				JarEntry jarEntry = (JarEntry) e.nextElement();
				if (!jarEntry.isDirectory() && jarEntry.getName().endsWith(".class")) {
					try {
						String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6).replace('/', '.');
						Object newInstance = cl.loadClass(className).newInstance();
						if (typeParameterClass.isInstance(newInstance)) {
							result.add(((T) newInstance));
							System.out.println("Plugin found: " + className + ", Jar file: " + jarFilePath);
							logger.info("Plugin found: " + className + " in " + jarFilePath);
						}
					} catch (Exception ec) {
						ec.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
