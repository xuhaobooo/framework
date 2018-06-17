package com.sz91online.mybatis.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.DefaultXmlFormatter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.codegen.ibatis2.sqlmap.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class GeneratorPlugin extends PluginAdapter {

	private Map<FullyQualifiedTable, List<XmlElement>> elementsToAdd;

	public GeneratorPlugin() {
		super();
		elementsToAdd = new HashMap<FullyQualifiedTable, List<XmlElement>>();
	}

	@Override
	public boolean validate(List<String> arg0) {
		return true;
	}

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

		List<GeneratedJavaFile> superGenerateList = new ArrayList<GeneratedJavaFile>();

		String objectName = introspectedTable.getTableConfiguration().getDomainObjectName();

		JavaFormatter format = new DefaultJavaFormatter();
		format.setContext(context);

		// simpleBean generator
		JavaModelGeneratorConfiguration config = context.getJavaModelGeneratorConfiguration();

		DefaultShellCallback callback = new DefaultShellCallback(false);
		try {
			File directory = callback.getDirectory(config.getTargetProject(), config.getTargetPackage());
			File targetFile = new File(directory, "Simple" + objectName + ".java");
			if (!targetFile.exists()) {
				FullyQualifiedJavaType parentClass = new FullyQualifiedJavaType(
						config.getTargetPackage() + "." + objectName);
				TopLevelClass topLevelClass = new TopLevelClass(config.getTargetPackage() + ".Simple" + objectName);
				topLevelClass.addImportedType(parentClass);
				topLevelClass.setSuperClass(parentClass);
				topLevelClass.setVisibility(JavaVisibility.PUBLIC);
				GeneratedJavaFile file = new GeneratedJavaFile(topLevelClass, config.getTargetProject(), format);
				superGenerateList.add(file);
			}

			JavaClientGeneratorConfiguration clientConfig = context.getJavaClientGeneratorConfiguration();
			File directory2 = callback.getDirectory(clientConfig.getTargetProject(), clientConfig.getTargetPackage());
			File targetFile2 = new File(directory2, objectName + "MapperExt.java");
			if (!targetFile2.exists()) {
				Interface clientClass = new Interface(clientConfig.getTargetPackage() + "." + objectName + "MapperExt");
				clientClass.setVisibility(JavaVisibility.PUBLIC);
				FullyQualifiedJavaType extParentClass = new FullyQualifiedJavaType(
						"com.sz91online.common.db.service.ISearchableDAO");
				clientClass.addImportedType(extParentClass);
				clientClass.addSuperInterface(extParentClass);
				GeneratedJavaFile clientFile = new GeneratedJavaFile(clientClass, clientConfig.getTargetProject(),
						format);
				superGenerateList.add(clientFile);
			}
		} catch (ShellException e) {
			e.printStackTrace();
			return superGenerateList;
		}
		return superGenerateList;
	}

	public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		addSaveElement(element, introspectedTable.getFullyQualifiedTable());
		return true;
	}

	public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		addDeleteElement(element, introspectedTable.getFullyQualifiedTable());
		return true;
	}

	public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		addSelectByBean(element, introspectedTable);
		return true;
	}

	public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		addUpdateByPrimaryKeySelective(element, introspectedTable);
		return true;
	}

	public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		return true;
	}

	private void addUpdateByPrimaryKeySelective(XmlElement element, IntrospectedTable introspectedTable) {
		FullyQualifiedTable fqt = introspectedTable.getFullyQualifiedTable();
		System.out.println("1");
		// 添加selectByBean节点
		XmlElement answer = new XmlElement("update");
		answer.addAttribute(new Attribute("id", "updateByPrimaryKeySelective"));

		String objectName = introspectedTable.getTableConfiguration().getDomainObjectName();
		JavaModelGeneratorConfiguration config = context.getJavaModelGeneratorConfiguration();
		answer.addAttribute(new Attribute("parameterType", config.getTargetPackage() + "." + objectName));

		StringBuilder sb = new StringBuilder();
		sb.append("update "); //$NON-NLS-1$
		sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());

		sb.append(" set "); //$NON-NLS-1$

		answer.addElement(new TextElement(sb.toString()));
		Iterator<IntrospectedColumn> iter = introspectedTable.getNonBLOBColumns().iterator();
		while (iter.hasNext()) {
			IntrospectedColumn introspectedColumn = iter.next();
			if (introspectedColumn.getActualColumnName()
					.equals(introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName())) {

				TextElement tl = new TextElement(" id= "
						+ MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, ""));
				answer.addElement(tl);
				continue;
			}

			String fieldName = MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn);

			XmlElement ifElement = new XmlElement("if");
			ifElement.addAttribute(new Attribute("test", introspectedColumn.getJavaProperty() + " != null"));

			TextElement tl = new TextElement("," + fieldName + " = "
					+ MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, ""));
			ifElement.addElement(tl);
			answer.addElement(ifElement);

		}

		answer.addElement(new TextElement(" where "
				+ introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName() + " = "
				+ MyBatis3FormattingUtilities.getParameterClause(introspectedTable.getPrimaryKeyColumns().get(0), "")));
		;

		List<XmlElement> elements = elementsToAdd.get(fqt);
		if (elements == null) {
			elements = new ArrayList<XmlElement>();
			elementsToAdd.put(fqt, elements);
		}
		elements.add(answer);
	}

	private void addSelectByBean(XmlElement element, IntrospectedTable introspectedTable) {
		FullyQualifiedTable fqt = introspectedTable.getFullyQualifiedTable();

		// 添加selectByBean节点
		XmlElement answer = new XmlElement("select");
		answer.addAttribute(new Attribute("id", "selectByBean"));

		answer.addAttribute(new Attribute("resultMap", "BaseResultMap"));

		String objectName = introspectedTable.getTableConfiguration().getDomainObjectName();
		JavaModelGeneratorConfiguration config = context.getJavaModelGeneratorConfiguration();
		answer.addAttribute(new Attribute("parameterType", config.getTargetPackage() + "." + objectName));

		StringBuilder sb = new StringBuilder();
		sb.append("select * from "); //$NON-NLS-1$
		sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());

		sb.append(" where 1=1 "); //$NON-NLS-1$

		answer.addElement(new TextElement(sb.toString()));

		Iterator<IntrospectedColumn> iter = introspectedTable.getNonBLOBColumns().iterator();
		while (iter.hasNext()) {
			IntrospectedColumn introspectedColumn = iter.next();
			XmlElement ifElement = new XmlElement("if");
			ifElement.addAttribute(new Attribute("test", introspectedColumn.getJavaProperty() + "!=null"));
			ifElement.addElement(
					new TextElement("and " + MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn)
							+ " = " + MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "")));

			answer.addElement(ifElement);

			if (iter.hasNext()) {
				sb.append(',');
			}

		}

		List<XmlElement> elements = elementsToAdd.get(fqt);
		if (elements == null) {
			elements = new ArrayList<XmlElement>();
			elementsToAdd.put(fqt, elements);
		}
		elements.add(answer);
	}

	private void addDeleteElement(XmlElement element, FullyQualifiedTable fqt) {
		XmlElement newElement = new XmlElement(element);

		// remove old id attribute and add a new one with the new name
		for (Iterator<Attribute> iterator = newElement.getAttributes().iterator(); iterator.hasNext();) {
			Attribute attribute = iterator.next();
			if ("id".equals(attribute.getName())) { //$NON-NLS-1$
				iterator.remove();
				Attribute idAttribute = new Attribute("id", "removeKeysWithSession"); //$NON-NLS-1$ //$NON-NLS-2$
				newElement.addAttribute(idAttribute);
				break;
			}
		}
		for (Iterator<Attribute> iterator = newElement.getAttributes().iterator(); iterator.hasNext();) {
			Attribute attribute = iterator.next();
			if ("parameterType".equals(attribute.getName())) {
				iterator.remove();
				Attribute typeAttribute = new Attribute("parameterType", "java.util.List"); //$NON-NLS-1$ //$NON-NLS-2$
				newElement.addAttribute(typeAttribute);
				break;
			}
		}

		newElement.getElements().clear();
		TextElement text = new TextElement("delete from " + fqt
				+ " where id IN <foreach close=\")\" collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\"> #{item} </foreach>");
		newElement.getElements().add(text);
		// save the new element locally. We'll add it to the document
		// later
		List<XmlElement> elements = elementsToAdd.get(fqt);
		if (elements == null) {
			elements = new ArrayList<XmlElement>();
			elementsToAdd.put(fqt, elements);
		}
		elements.add(newElement);
	}

	private void addSaveElement(XmlElement element, FullyQualifiedTable fqt) {
		XmlElement newElement = new XmlElement(element);

		// remove old id attribute and add a new one with the new name
		for (Iterator<Attribute> iterator = newElement.getAttributes().iterator(); iterator.hasNext();) {
			Attribute attribute = iterator.next();
			if ("id".equals(attribute.getName())) { //$NON-NLS-1$
				iterator.remove();
				Attribute idAttribute = new Attribute("id", "insertAndReturnKey"); //$NON-NLS-1$ //$NON-NLS-2$
				Attribute keyAttribute = new Attribute("keyProperty", "id");
				Attribute genAttribute = new Attribute("useGeneratedKeys", "true");

				newElement.addAttribute(idAttribute);
				newElement.addAttribute(keyAttribute);
				newElement.addAttribute(genAttribute);
				break;
			}
		}
		// save the new element locally. We'll add it to the document
		// later
		List<XmlElement> elements = elementsToAdd.get(fqt);
		if (elements == null) {
			elements = new ArrayList<XmlElement>();
			elementsToAdd.put(fqt, elements);
		}
		elements.add(newElement);
	}

	@Override
	public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
		Document document = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
				XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
		document.setRootElement(getSqlMapElement(introspectedTable));

		String objectName = introspectedTable.getTableConfiguration().getDomainObjectName();

		SqlMapGeneratorConfiguration config = context.getSqlMapGeneratorConfiguration();
		List<GeneratedXmlFile> superGenerateList = new ArrayList<GeneratedXmlFile>();

		DefaultShellCallback callback = new DefaultShellCallback(false);
		try {
			File directory = callback.getDirectory(config.getTargetProject(), config.getTargetPackage());
			File targetFile = new File(directory, objectName + "MapperExt.xml");
			if (!targetFile.exists()) {
				DefaultXmlFormatter format = new DefaultXmlFormatter();
				format.setContext(context);
				GeneratedXmlFile file = new GeneratedXmlFile(document, objectName + "MapperExt.xml",
						config.getTargetPackage(), config.getTargetProject(), false, format);

				superGenerateList.add(file);
			}
		} catch (Exception e) {

		}

		return superGenerateList;
	}

	protected XmlElement getSqlMapElement(IntrospectedTable introspectedTable) {
		String objectName = introspectedTable.getTableConfiguration().getDomainObjectName();
		JavaClientGeneratorConfiguration clientConfig = context.getJavaClientGeneratorConfiguration();
		XmlElement answer = new XmlElement("mapper"); //$NON-NLS-1$
		answer.addAttribute(new Attribute("namespace", //$NON-NLS-1$
				clientConfig.getTargetPackage() + "." + objectName + "MapperExt"));

		context.getCommentGenerator().addRootComment(answer);
		addBaseColumnListElement(answer, introspectedTable);
		return answer;
	}

	protected void addBaseColumnListElement(XmlElement parentElement, IntrospectedTable introspectedTable) {
		if (introspectedTable.getRules().generateBaseColumnList()) {
			AbstractXmlElementGenerator elementGenerator = new ExtResultMapGenerator();
			initializeAndExecuteGenerator(elementGenerator, parentElement, introspectedTable);
		}
	}

	protected void initializeAndExecuteGenerator(AbstractXmlElementGenerator elementGenerator, XmlElement parentElement,
			IntrospectedTable introspectedTable) {
		elementGenerator.setContext(context);
		elementGenerator.setIntrospectedTable(introspectedTable);
		elementGenerator.setProgressCallback(null);
		elementGenerator.setWarnings(new ArrayList<String>());
		elementGenerator.addElements(parentElement);
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		List<XmlElement> elements = elementsToAdd.get(introspectedTable.getFullyQualifiedTable());
		System.out.println("-------------------" + elements);
		if (elements != null) {
			System.out.println("size:" + elementsToAdd.size());
			for (XmlElement element : elements) {
				document.getRootElement().addElement(element);

			}
		}

		return true;
	}

}
