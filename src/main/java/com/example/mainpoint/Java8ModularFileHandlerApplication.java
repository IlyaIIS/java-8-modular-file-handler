package com.example.mainpoint;

import org.example.FileHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.*;

@ComponentScan({"org.example"})
@SpringBootApplication
public class Java8ModularFileHandlerApplication {

	public static void main(String[] args) throws IOException {
		ApplicationContext applicationContext = SpringApplication.run(Java8ModularFileHandlerApplication.class, args);

		System.out.println("\nПривет, это приложение может обработать предложенный файл выбранным способом. Доступные обработчики: ");

		List<FileHandler> handlers = new java.util.ArrayList<FileHandler>();

		for (String beanName : applicationContext.getBeanDefinitionNames())
			if (beanName.contains("FileHandler"))
				if (!beanName.equalsIgnoreCase(MethodHandles.lookup().lookupClass().getSimpleName())){
					FileHandler handler = (FileHandler) applicationContext.getBean(beanName);
					handlers.add(handler);
					System.out.println(beanName);
				}
		System.out.println();

		while (true) {
			executeMainLoop(handlers);
			System.out.println("\nНачать сначала? (y/n) ");
			if (System.in.read() == 'n')
				break;
		}
	}

	private static void executeMainLoop(List<FileHandler> handlers){
		while (true) {
			System.out.print("Введите абсолютный путь до файла обработки: ");
			Scanner in = new Scanner(System.in);
			String path = in.next();
			System.out.println();

			File file = new File(path);
			if (!file.exists()){
				System.out.println("Файл не найден!");
				continue;
			}

			for (FileHandler handler : handlers) {
				if (handler.isExtensionCorrect(file)){
					String functionName = executeFunctionSelection(handler);

					Object result = handler.Invoke(functionName, file);

					System.out.println("Результат обработки файла функцией: " + result);
					return;
				}
			}

			System.out.println("Нет подходящего обработчига для данного формата файла!");
		}
	}

	private static String executeFunctionSelection(FileHandler handler){
		System.out.println("Выбран обработчик: " + handler.getClass().getSimpleName());
		System.out.println("Доступные функции: ");

		Map<String, String> funcsWithDisc = handler.getFunctionsNamesWithDescriptions();

		for (Map.Entry<String, String> funcWithDisc: funcsWithDisc.entrySet())
			System.out.println("\t" + funcWithDisc.getKey() + " - " + funcWithDisc.getValue());
		while (true) {
			System.out.print("\nВведите название выбранной функции: ");
			Scanner in = new Scanner(System.in);
			String functionName = in.next();

			if (!funcsWithDisc.containsKey(functionName)){
				System.out.println("Не верное название функции!");
				continue;
			} else {
				return functionName;
			}
		}
	}

}
