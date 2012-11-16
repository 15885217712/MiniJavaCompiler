package minijava.dexbuilder;

import java.io.*;
import java.util.*;

import util.*;

import minijava.dexstruct.*;

/**
 * �ύ׼���õ������Ϣ�ͱ���õĶ����ƴ��룬<br/>
 * �������յ�dex�ļ�����
 *
 */
public class DexPrinter {
	
	FileOutputStream target = null;
	
	//7����Ϣ�б�
	Header dexHeader;
	DexStringTable dexStringTable;
	DexTypeTable dexTypeTable;
	DexProtoTable dexProtoTable;
	DexFieldTable dexFieldTable;
	DexMethodTable dexMethodTable;
	ClassDef dexClassDef;
	
	//��֤��������
	ReVerify dexReVerifier;
	
	//��Ϣ������
	int codeItemCount;
	int typeListCount;
	int dataBegin;
	String mainClassNameString;
	
	//���뻺����
	ArrayList<Byte> outBuffer;
	int pos;
	
	/**
	 * ���캯��
	 * @param file ����Ϊ����ļ��� һ��Ϊ��classes.dex��
	 */
	public DexPrinter(String file) 
	{
		target = SdcardManager.getFileOutputStream(file);
		dexHeader = new Header();
		dexStringTable = new DexStringTable();
		dexTypeTable = new DexTypeTable(dexStringTable);
		dexProtoTable = new DexProtoTable(dexTypeTable, dexStringTable);
		dexFieldTable = new DexFieldTable(dexTypeTable, dexStringTable);
		dexMethodTable = new DexMethodTable(dexStringTable, dexTypeTable, dexProtoTable);
		dexClassDef = new ClassDef(dexStringTable, dexTypeTable, dexFieldTable, dexMethodTable);
		outBuffer = new ArrayList<Byte>();
		pos = 0;
		codeItemCount = 0;
		typeListCount = 0;
		dexReVerifier = new ReVerify();
	}
	
	/**
	 * ���Դ�����к���main�������������
	 * @param _mainClassNameString ����main������������ƣ���Ҫ���κ�����
	 */
	public void AddMainClassName(String _mainClassNameString)
	{
		mainClassNameString = _mainClassNameString;
	}
	
	/**
	 * �������Ϣ��ע��һ����
	 * @param _className �����ƣ���Ҫ�� L �� ��
	 * @param _superClassName �����ĸ������ƣ���Ҫ�� L �� ��
	 */
	public void AddClass(String _className, String _superClassName)
	{
		//TODO
		_className = FixPoint(_className);
		_superClassName = FixPoint(_superClassName);
		
		_className = FixClassType(_className);
		_superClassName = FixClassType(_superClassName);
		String srcFileNameString = new String(mainClassNameString + ".java");
		dexClassDef.AddClass(_className, _superClassName, srcFileNameString);
	}
	
	/**
	 * �������Ϣ��ע��һ����<br/>
	 * �ڲ����ã�����ָ����������Դ�ļ����Լ���ķ���Ȩ�ޣ��������GUI֧������Ϣ
	 * @param _className �����ƣ���Ҫ�� L �� ��
	 * @param _superClassName �����ĸ������ƣ���Ҫ�� L �� ��
	 * @param _srcFileName �����������Դ�ļ�����
	 * @param _accessFlag �����ķ���Ȩ��
	 */
	private void LCAddClass(String _className, String _superClassName, String _srcFileName, int _accessFlag)
	{
		//TODO
		_className = FixPoint(_className);
		_superClassName = FixPoint(_superClassName);
		
		_className = FixClassType(_className);
		_superClassName = FixClassType(_superClassName);
		dexClassDef.LCAddClass(_className, _superClassName, _srcFileName, _accessFlag);
	}
	
	/**
	 * �������Ϣ����һ�������һ����̬��Ա���ԣ������ע���
	 * @param _className ��Ӧ������ƣ���Ҫ�� L �� ��
	 * @param _fieldType ��Ӧ��Ա�������ͣ���Ҫ�� L �� ��
	 * @param _fieldName ��Ӧ��Ա�������֣���Ҫ�� L �� ��
	 */
	public void AddStaticFieldToClass(String _className, String _fieldType, String _fieldName)
	{
		//TODO
		_className = FixPoint(_className);
		_fieldType = FixPoint(_fieldType);
		_fieldName = FixPoint(_fieldName);
		
		_className = FixClassType(_className);
		_fieldType = FixClassType(_fieldType);
		dexClassDef.AddStaticFieldToClass(_className, _fieldType, _fieldName);
	}

	/**
	 * �������Ϣ����һ�������һ����̬��Ա���ԣ������ע���<br/>
	 * �ڲ����ã�����ָ����̬��Ա���Եķ���Ȩ�ޣ��������GUI֧������Ϣ
	 * @param _className ��Ӧ������ƣ���Ҫ�� L �� ��
	 * @param _fieldType ��Ӧ��Ա�������ͣ���Ҫ�� L �� ��
	 * @param _fieldName ��Ӧ��Ա�������֣���Ҫ�� L �� ��
	 * @param _accessFlag ��Ӧ��Ա���Է���Ȩ��
	 */
	private void LCAddStaticFieldToClass(String _className, String _fieldType, String _fieldName, int _accessFlag)
	{
		//TODO
		_className = FixPoint(_className);
		_fieldType = FixPoint(_fieldType);
		_fieldName = FixPoint(_fieldName);
		
		_className = FixClassType(_className);
		_fieldType = FixClassType(_fieldType);
		dexClassDef.LCAddStaticFieldToClass(_className, _fieldType, _fieldName, _accessFlag);
	}
	
	/**
	 * �������Ϣ����һ�������һ���Ǿ�̬��Ա���ԣ������ע���
	 * @param _className ��Ӧ������ƣ���Ҫ�� L �� ��
	 * @param _fieldType ��Ӧ��Ա�������ͣ���Ҫ�� L �� ��
	 * @param _fieldName ��Ӧ��Ա�������֣���Ҫ�� L �� ��
	 */
	public void AddInstanceFieldToClass(String _className, String _fieldType, String _fieldName)
	{
		//TODO
		_className = FixPoint(_className);
		_fieldType = FixPoint(_fieldType);
		_fieldName = FixPoint(_fieldName);

		_className = FixClassType(_className);
		_fieldType = FixClassType(_fieldType);
		dexClassDef.AddInstanceFieldToClass(_className, _fieldType, _fieldName);
	}
	
	/**
	 * �������Ϣ����һ�������һ����̬�����������ע���
	 * @param _className ��Ӧ�����֣���Ҫ�� L �� ��
	 * @param _methodName ��Ӧ��������
	 * @param _returnType ��Ӧ��������ֵ���ͣ���Ҫ�� L �� ��
	 * @param _accessFlag ��Ӧ�����ķ���Ȩ��
	 * @param _paramNum ��Ӧ�����Ĳ�������
	 * @param _params ��Ӧ�������в������ͣ�����˳�򣬲�Ҫ�� L �� ��
	 */
	public void AddDirectMethodToClass(String _className, String _methodName, String _returnType, int _accessFlag,
			int _paramNum, String[] _params)
	{
		//TODO
		_className = FixPoint(_className);
		_methodName = FixPoint(_methodName);
		_returnType = FixPoint(_returnType);
		
		_className = FixClassType(_className);
		_returnType = FixClassType(_returnType);
		for (int i = 0; i < _paramNum; i++) 
		{
			//TODO
			_params[i] = FixPoint(_params[i]);
			
			_params[i] = FixClassType(_params[i]); 
		}
		dexClassDef.AddDirectMethodToClass(_className, _methodName, _returnType, _accessFlag, _paramNum, _params);
	}
	
	/**
	 * �������Ϣ����һ�������һ���Ǿ�̬�����������ע���
	 * @param _className ��Ӧ�����֣���Ҫ�� L �� ��
	 * @param _methodName ��Ӧ��������
	 * @param _returnType ��Ӧ��������ֵ���ͣ���Ҫ�� L �� ��
	 * @param _accessFlag ��Ӧ�����ķ���Ȩ��
	 * @param _paramNum ��Ӧ�����Ĳ�������
	 * @param _params ��Ӧ�������в������ͣ�����˳�򣬲�Ҫ�� L �� ��
	 */
	public void AddVirtualMethodToClass(String _className, String _methodName, String _returnType, int _accessFlag,
			int _paramNum, String[] _params)
	{
		//TODO
		_className = FixPoint(_className);
		_methodName = FixPoint(_methodName);
		_returnType = FixPoint(_returnType);
		
		_className = FixClassType(_className);
		_returnType = FixClassType(_returnType);
		for (int i = 0; i < _paramNum; i++) 
		{
			//TODO
			_params[i] = FixPoint(_params[i]);
			
			_params[i] = FixClassType(_params[i]); 
		}
		dexClassDef.AddVirtualMethodToClass(_className, _methodName, _returnType, _accessFlag, _paramNum, _params);
	}
	
	/**
	 * ��ӱ���õĶ����ƴ��룺��һ��������ӱ���õĴ���
	 * @param _className ���������������ƣ���Ҫ�� L �� ��
	 * @param _methodName ��������
	 * @param _registersSize ʹ�õļĴ�������
	 * @param _insSize ����Ĵ�������
	 * @param _outsSize �ڲ�����ʹ�õļĴ�������
	 * @param _insnsSize �����ƴ��볤�ȣ���2Byte�Ƶĸ�����
	 * @param _code �����ƴ�������
	 */
	public void AddMethodCodeToMethod(String _className, String _methodName, 
			short _registersSize, short _insSize, short _outsSize, int _insnsSize, byte[] _code)
	{
		//TODO
		_className = FixPoint(_className);
		_methodName = FixPoint(_methodName);
		
		_className = FixClassType(_className);
		dexClassDef.AddMethodCodeToMethod(_className, _methodName, _registersSize, _insSize, _outsSize, _insnsSize, _code);
	}
	
	/**
	 * �������Ϣ�����һ������<br/>
	 * �����������û���õ�
	 * @param _localType ԭʼ״̬���ַ���
	 */
	public void AddLocalType(String _localType)
	{
		_localType = FixPoint(_localType);
		_localType = FixClassType(_localType);
		dexTypeTable.AddType(_localType);
	}
	
	/**
	 * ͨ��һ���ַ�������ȡ����ַ�����StringTable�е�index
	 * @param _string Ҫ��ѯindex���ַ���
	 * @return ���ض�Ӧ��index
	 */
	public int GetStringIdx(String _string)
	{
		//TODO
		_string = FixPoint(_string);
		
		return dexStringTable.GetStringIdx(_string);
	}
	
	/**
	 * ͨ��һ�����������ַ�������ȡ����ַ�����TypeTable�е�index
	 * @param _type Ҫ��ѯ�����������ַ�������Ҫ�� L �� ��
	 * @return ���ض�Ӧ��index
	 */
	public int GetTypeIdx(String _type)
	{
		//TODO
		_type = FixPoint(_type);
		
		_type = FixClassType(_type);
		return dexTypeTable.GetTypeIdx(_type);
	}
	
	/**
	 * ͨ��һ����Ա���Ե���Ϣ����ȡ�����Ա������FieldTable�е�index
	 * @param _class ��Ӧ��Ա����������������ƣ���Ҫ�� L �� ��
	 * @param _type ��Ӧ��Ա���Ե��������ƣ���Ҫ�� L �� ��
	 * @param _name ��Ӧ��Ա���Ե�����
	 * @return ���ض�Ӧ��index
	 */
	public int GetFieldIdx(String _class, String _type, String _name)
	{
		//TODO
		_class = FixPoint(_class);
		_type = FixPoint(_type);
		_name = FixPoint(_name);

		_class = FixClassType(_class);
		_type = FixClassType(_type);
		return dexFieldTable.GetFieldIdxByClassAndTypeAndName(_class, _type, _name);
	}
	
	/**
	 * ͨ��һ����Ա��������Ϣ����ȡ�����Ա������MethodTable�е�index
	 * @param _className ��Ӧ��Ա��������������ƣ���Ҫ�� L �� ��
	 * @param _methodName ��Ӧ��Ա����������
	 * @return ���ض�Ӧ��index
	 */
	public int GetMethodIdx(String _className, String _methodName)
	{
		//TODO
		_className = FixPoint(_className);
		_methodName = FixPoint(_methodName);
		
		_className = FixClassType(_className);
		return dexMethodTable.GetMethodIdxByInformation(_className, _methodName);
	}

	/**
	 * ͨ��һ����Ա��������Ϣ����ȡ�����Ա������MethodTable�е�index<br />
	 * �ڲ����ã�ӵ�и�����ϢȨ�ޣ���ΪҪ����һ�����ع����ṩGUI֧�ֵ�ϵͳ����
	 * @param _className ��Ӧ��Ա��������������ƣ���Ҫ�� L �� ��
	 * @param _methodName ��Ӧ��Ա����������
	 * @param _paramNum ��Ӧ��Ա�����Ĳ�������
	 * @param _params ��Ӧ��Ա����������������������
	 * @return ���ض�Ӧ��index
	 */
	private int LCGetMethodIdx(String _className, String _methodName, int _paramNum, String[] _params)
	{
		//TODO
		_className = FixPoint(_className);
		_methodName = FixPoint(_methodName);
		
		_className = FixClassType(_className);
		String modifiedMethod = new String(_className + _methodName);
		for (int i = 0; i < _paramNum; i++) 
		{
			modifiedMethod += _params[i];
		}
		return dexMethodTable.GetMethodIdxByModifiedMethod(modifiedMethod);
	}
	
	/**
	 * ͨ��index��ȡһ����Ա���Ե�����
	 * @param _fieldidx Ҫ��ѯ��index
	 * @return ����һ����Ա���Ե�����
	 */
	public String GetFieldNameByIdx(int _fieldidx)
	{
		return dexStringTable.GetString(dexFieldTable.GetFieldNameIdx(_fieldidx));
	}
	
	/**
	 * ͨ��index��ȡһ�����͵�����
	 * @param _typeidx Ҫ��ѯ��index
	 * @return ���ؽ�����L�ͣ��Ͻ������String������Get
	 */
	public String GetTypeNameByIdx(int _typeidx)
	{
		return dexTypeTable.GetTypeString(_typeidx);
	}
	
	/**
	 * ͨ��index��ȡһ������������
	 * @param _methodidx Ҫ��ѯ��index
	 * @return ���ض�Ӧ�ķ�������
	 */
	public String GetMethodNameByIdx(int _methodidx) {
		return dexStringTable.GetString(dexMethodTable.GetMethodNameIdx(_methodidx));
	}
	
	/**
	 * ��������ǰ��Ԥ������Ŀ<br/>
	 * ������Ҫ�����GUI֧�ֵ�ϵͳ����<br/>
	 * ��Ϊÿһ�����һ��Ĭ�ϵĹ��캯��
	 */
	private void Pretreatment()
	{
		String[] tmpempty = new String[0];
		dexMethodTable.AddMethod("Ljava/lang/Object;", "<init>", "V", 0, tmpempty);

		if (!Mode.IsOutputApk()) 
		{
			dexFieldTable.AddField("Ljava/lang/System;", "Ljava/io/PrintStream;", "out");
			String[] tmp = new String[1];
			tmp[0] = "I";
			dexMethodTable.AddMethod("Ljava/io/PrintStream;", "println", "V", 1, tmp);
		}
		else 
		{
			AddApkNotDefinedThings();
			AddApkClasses();
		}

		dexClassDef.ReFreshSize();
		
		for (int i = 0; i < dexClassDef.GetSize(); i++) 
		{
			String[] params = new String[0];
			String initNameString = DeFixClassType(dexClassDef.GetClassDefItem(i).classNameString);
			AddDirectMethodToClass(initNameString, "<init>", "V", 65537, 0, params);
		}
		
	}
	
	/**
	 * ���GUI֧�ֵ�һ����<br/>
	 * ������������GUI֧���е��õ�һЩ�⺯��������ȵȵ�<br/>
	 * ������������Ϣ�е���Ϣ
	 */
	private void AddApkNotDefinedThings() 
	{
		String[] params;
		
		dexTypeTable.AddType("Ljava/lang/StringBuilder;");
		params = new String[1];
		params[0] = new String("Ljava/lang/Object;");
		dexMethodTable.AddMethod("Ljava/lang/String;", "valueOf", "Ljava/lang/String;", 1, params);
		params = new String[1];
		params[0] = new String("Ljava/lang/String;");
		dexMethodTable.AddMethod("Ljava/lang/StringBuilder;", "<init>", "V", 1, params);
		params = new String[1];
		params[0] = new String("I");
		dexMethodTable.LCAddMethod("Ljava/lang/StringBuilder;", "append", "Ljava/lang/StringBuilder;", 1, params);
		params = new String[1];
		params[0] = new String("C");
		dexMethodTable.LCAddMethod("Ljava/lang/StringBuilder;", "append", "Ljava/lang/StringBuilder;", 1, params);
		params = new String[0];
		dexMethodTable.AddMethod("Ljava/lang/StringBuilder;", "toString", "Ljava/lang/String;", 0, params);
		params = new String[0];
		dexMethodTable.AddMethod("Landroid/app/Activity;", "<init>", "V", 0, params);
		params = new String[1];
		params[0] = new String("Landroid/os/Bundle;");
		dexMethodTable.AddMethod("Landroid/app/Activity;", "onCreate", "V", 1, params);
		params = new String[1];
		params[0] = new String("I");
		dexMethodTable.AddMethod("Lminijava/output/MiniJavaOutputActivity;", "setContentView", "V", 1, params);
		params = new String[1];
		params[0] = new String("I");
		dexMethodTable.AddMethod("Lminijava/output/MiniJavaOutputActivity;", "findViewById", "Landroid/view/View;", 1, params);
		params = new String[1];
		params[0] = new String("Ljava/lang/CharSequence;");
		dexMethodTable.AddMethod("Landroid/widget/TextView;", "setText", "V", 1, params);
		
		dexStringTable.AddString("");
		dexStringTable.AddString("Designed By ZongZiWang, LIuCHi, LynnXie 2011-2036 All Rights Reserved ^_^\n");
	}
	
	/**
	 * ���GUI֧�ֵ�һ����<br/>
	 * �������������ṩGUI֧�ֵ�����Ϣ
	 */
	private void AddApkClasses() 
	{
		String[] params;

		//class #0
		LCAddClass("minijava/output/MiniJavaOutput", "java/lang/Object", "MiniJavaOutput.java", 1);
		LCAddStaticFieldToClass("minijava/output/MiniJavaOutput", "java/lang/String", "answer", 12);
		params = new String[0];
		AddDirectMethodToClass("minijava/output/MiniJavaOutput", "<clinit>", "void", 65544, 0, params);
		params = new String[1];
		params[0] = new String("int");
		AddDirectMethodToClass("minijava/output/MiniJavaOutput", "addAnswer", "boolean", 9, 1, params);
		params = new String[0];
		AddDirectMethodToClass("minijava/output/MiniJavaOutput", "getAnswer", "java/lang/String", 9, 0, params);
		params = new String[0];
		AddDirectMethodToClass("minijava/output/MiniJavaOutput", "init", "void", 9, 0, params);
		
		//class #1
		LCAddClass("minijava/output/MiniJavaOutputActivity", "android/app/Activity", "MiniJavaOutputActivity.java", 1);
		params = new String[1];
		params[0] = new String("android/os/Bundle");
		AddVirtualMethodToClass("minijava/output/MiniJavaOutputActivity", "onCreate", "void", 1, 1, params);
		params = new String[0];
		AddVirtualMethodToClass("minijava/output/MiniJavaOutputActivity", "test", "void", 1, 0, params);
		
		//class #2
		LCAddClass("minijava/output/R$attr", "java/lang/Object", "R.java", 17);
		
		//class #3
		LCAddClass("minijava/output/R$drawable", "java/lang/Object", "R.java", 17);
		LCAddStaticFieldToClass("minijava/output/R$drawable", "int", "ic_launcher", 25);
		
		//class #4
		LCAddClass("minijava/output/R$id", "java/lang/Object", "R.java", 17);
		LCAddStaticFieldToClass("minijava/output/R$id", "int", "sv1", 25);
		LCAddStaticFieldToClass("minijava/output/R$id", "int", "tv1", 25);
		
		//class #5
		LCAddClass("minijava/output/R$layout", "java/lang/Object", "R.java", 17);
		LCAddStaticFieldToClass("minijava/output/R$layout", "int", "main", 25);
		
		//class #6
		LCAddClass("minijava/output/R$string", "java/lang/Object", "R.java", 17);
		LCAddStaticFieldToClass("minijava/output/R$string", "int", "app_name", 25);
		
		//class #7
		LCAddClass("minijava/output/R", "java/lang/Object", "R.java", 17);
	}
	
	/**
	 * ���GUI֧�ֵ�һ����<br/>
	 * ��GUI֧�ֵ���ĺ�������Ӵ���
	 */
	private void AddCodeToApkClasses() 
	{
		String className;
		byte[] code;
		int codeLength;
		int stringIdx;
		int fieldIdx;
		int typeIdx;
		int methodIdx;
		String[] params;
		
		//#0
		className = new String("minijava/output/MiniJavaOutput");
		
		codeLength = 5;
		code = new byte[codeLength*2];
		code[0] = (byte)26;
		code[1] = (byte)0;
		stringIdx = dexStringTable.GetStringIdx("");
		code[2] = (byte)stringIdx;
		stringIdx = stringIdx >>> 8;
		code[3] = (byte)stringIdx;
		code[4] = (byte)105;
		code[5] = (byte)0;
		fieldIdx = GetFieldIdx("minijava/output/MiniJavaOutput", "java/lang/String", "answer");
		code[6] = (byte)fieldIdx;
		fieldIdx = fieldIdx >>> 8;
		code[7] = (byte)fieldIdx;
		code[8] = (byte)14;
		code[9] = (byte)0;
		AddMethodCodeToMethod(className, "<clinit>", (short)1, (short)0, (short)0, codeLength, code);
		
		codeLength = 29;
		code = new byte[codeLength*2];
		code[0] = (byte)34;
		code[1] = (byte)0;
		typeIdx = GetTypeIdx("java/lang/StringBuilder");
		code[2] = (byte)typeIdx;
		typeIdx = typeIdx >>> 8;
		code[3] = (byte)typeIdx;
		code[4] = (byte)98;
		code[5] = (byte)1;
		fieldIdx = GetFieldIdx("minijava/output/MiniJavaOutput", "java/lang/String", "answer");
		code[6] = (byte)fieldIdx;
		fieldIdx = fieldIdx >>> 8;
		code[7] = (byte)fieldIdx;
		code[8] = (byte)113;
		code[9] = (byte)16;
		methodIdx = GetMethodIdx("java/lang/String", "valueOf");
		code[10] = (byte)methodIdx;
		methodIdx = methodIdx >>> 8;
		code[11] = (byte)methodIdx;
		code[12] = (byte)1;
		code[13] = (byte)0;
		code[14] = (byte)12;
		code[15] = (byte)1;
		code[16] = (byte)112;
		code[17] = (byte)32;
		methodIdx = GetMethodIdx("java/lang/StringBuilder", "<init>");
		code[18] = (byte)methodIdx;
		methodIdx = methodIdx >>> 8;
		code[19] = (byte)methodIdx;
		code[20] = (byte)16;
		code[21] = (byte)0;
		code[22] = (byte)110;
		code[23] = (byte)32;
		params = new String[1];
		params[0] = new String("I");
		methodIdx = LCGetMethodIdx("java/lang/StringBuilder", "append", 1, params);
		code[24] = (byte)methodIdx;
		methodIdx = methodIdx >>> 8;
		code[25] = (byte)methodIdx;
		code[26] = (byte)32;
		code[27] = (byte)0;
		code[28] = (byte)12;
		code[29] = (byte)0;
		code[30] = (byte)19;
		code[31] = (byte)1;
		code[32] = (byte)10;
		code[33] = (byte)0;
		code[34] = (byte)110;
		code[35] = (byte)32;
		params = new String[1];
		params[0] = new String("C");
		methodIdx = LCGetMethodIdx("java/lang/StringBuilder", "append", 1, params);
		code[36] = (byte)methodIdx;
		methodIdx = methodIdx >>> 8;
		code[37] = (byte)methodIdx;
		code[38] = (byte)16;
		code[39] = (byte)0;
		code[40] = (byte)12;
		code[41] = (byte)0;
		code[42] = (byte)110;
		code[43] = (byte)16;
		methodIdx = GetMethodIdx("java/lang/StringBuilder", "toString");
		code[44] = (byte)methodIdx;
		methodIdx = methodIdx >>> 8;
		code[45] = (byte)methodIdx;
		code[46] = (byte)0;
		code[47] = (byte)0;
		code[48] = (byte)12;
		code[49] = (byte)0;
		code[50] = (byte)105;
		code[51] = (byte)0;
		fieldIdx = GetFieldIdx("minijava/output/MiniJavaOutput", "java/lang/String", "answer");
		code[52] = (byte)fieldIdx;
		fieldIdx = fieldIdx >>> 8;
		code[53] = (byte)fieldIdx;
		code[54] = (byte)18;
		code[55] = (byte)16;
		code[56] = (byte)15;
		code[57] = (byte)0;
		AddMethodCodeToMethod(className, "addAnswer", (short)3, (short)1, (short)2, codeLength, code);
		
		codeLength = 3;
		code = new byte[codeLength*2];
		code[0] = (byte)98;
		code[1] = (byte)0;
		fieldIdx = GetFieldIdx("minijava/output/MiniJavaOutput", "java/lang/String", "answer");
		code[2] = (byte)fieldIdx;
		fieldIdx = fieldIdx >>> 8;
		code[3] = (byte)fieldIdx;
		code[4] = (byte)17;
		code[5] = (byte)0;
		AddMethodCodeToMethod(className, "getAnswer", (short)1, (short)0, (short)0, codeLength, code);
		
		codeLength = 5;
		code = new byte[codeLength*2];
		code[0] = (byte)26;
		code[1] = (byte)0;
		stringIdx = GetStringIdx("Designed By ZongZiWang, LIuCHi, LynnXie 2011-2036 All Rights Reserved ^_^\n");
		code[2] = (byte)stringIdx;
		stringIdx = stringIdx >>> 8;
		code[3] = (byte)stringIdx;
		code[4] = (byte)105;
		code[5] = (byte)0;
		fieldIdx = GetFieldIdx("minijava/output/MiniJavaOutput", "java/lang/String", "answer");
		code[6] = (byte)fieldIdx;
		fieldIdx = fieldIdx >>> 8;
		code[7] = (byte)fieldIdx;
		code[8] = (byte)14;
		code[9] = (byte)0;
		AddMethodCodeToMethod(className, "init", (short)1, (short)0, (short)0, codeLength, code);
		
		//#1
		className = new String("minijava/output/MiniJavaOutputActivity");
		
		codeLength = 31;
		code = new byte[codeLength*2];
		code[0] = (byte)111;
		code[1] = (byte)32;
		methodIdx = GetMethodIdx("android/app/Activity", "onCreate");
		code[2] = (byte)methodIdx;
		methodIdx = methodIdx >>> 8;
		code[3] = (byte)methodIdx;
		code[4] = (byte)50;
		code[5] = (byte)0;
		code[6] = (byte)21;
		code[7] = (byte)1;
		code[8] = (byte)3;
		code[9] = (byte)127;
		code[10] = (byte)110;
		code[11] = (byte)32;
		methodIdx = GetMethodIdx("minijava/output/MiniJavaOutputActivity", "setContentView");
		code[12] = (byte)methodIdx;
		methodIdx = methodIdx >>> 8;
		code[13] = (byte)methodIdx;
		code[14] = (byte)18;
		code[15] = (byte)0;
		code[16] = (byte)113;
		code[17] = (byte)0;
		methodIdx = GetMethodIdx("minijava/output/MiniJavaOutput", "init");
		code[18] = (byte)methodIdx;
		methodIdx = methodIdx >>> 8;
		code[19] = (byte)methodIdx;
		code[20] = (byte)0;
		code[21] = (byte)0;
		code[22] = (byte)110;
		code[23] = (byte)16;
		methodIdx = GetMethodIdx("minijava/output/MiniJavaOutputActivity", "test");
		code[24] = (byte)methodIdx;
		methodIdx = methodIdx >>> 8;
		code[25] = (byte)methodIdx;
		code[26] = (byte)2;
		code[27] = (byte)0;
		code[28] = (byte)20;
		code[29] = (byte)1;
		code[30] = (byte)1;
		code[31] = (byte)0;
		code[32] = (byte)5;
		code[33] = (byte)127;
		code[34] = (byte)110;
		code[35] = (byte)32;
		methodIdx = GetMethodIdx("minijava/output/MiniJavaOutputActivity", "findViewById");
		code[36] = (byte)methodIdx;
		methodIdx = methodIdx >>> 8;
		code[37] = (byte)methodIdx;
		code[38] = (byte)18;
		code[39] = (byte)0;
		code[40] = (byte)12;
		code[41] = (byte)0;
		code[42] = (byte)31;
		code[43] = (byte)0;
		typeIdx = GetTypeIdx("android/widget/TextView");
		code[44] = (byte)typeIdx;
		typeIdx = typeIdx >>> 8;
		code[45] = (byte)typeIdx;
		code[46] = (byte)113;
		code[47] = (byte)0;
		methodIdx = GetMethodIdx("minijava/output/MiniJavaOutput", "getAnswer");
		code[48] = (byte)methodIdx;
		methodIdx = methodIdx >>> 8;
		code[49] = (byte)methodIdx;
		code[50] = (byte)0;
		code[51] = (byte)0;
		code[52] = (byte)12;
		code[53] = (byte)1;
		code[54] = (byte)110;
		code[55] = (byte)32;
		methodIdx = GetMethodIdx("android/widget/TextView", "setText");
		code[56] = (byte)methodIdx;
		methodIdx = methodIdx >>> 8;
		code[57] = (byte)methodIdx;
		code[58] = (byte)16;
		code[59] = (byte)0;
		code[60] = (byte)14;
		code[61] = (byte)0;
		AddMethodCodeToMethod(className, "onCreate", (short)4, (short)2, (short)2, codeLength, code);
		
		codeLength = 5;//TODO :this need change!
		code = new byte[codeLength*2];
		methodIdx = GetMethodIdx(mainClassNameString, "main");
		code[0] = (byte)18;
		code[1] = (byte)0;
		code[2] = (byte)113;
		code[3] = (byte)16;
		code[4] = (byte)methodIdx;
		methodIdx = methodIdx >>> 8;
		code[5] = (byte)methodIdx;
		code[6] = (byte)0;
		code[7] = (byte)0;
		code[8] = (byte)14;
		code[9] = (byte)0;
		AddMethodCodeToMethod(className, "test", (short)2, (short)1, (short)1, codeLength, code);
	}
	
	/**
	 * ��ÿһ��ע�����Ĭ�Ϲ��캯����Ӷ�Ӧ�Ĵ���<br/>
	 * ��������Զ�����
	 */
	private void AddInitToAllClass()
	{
		if (Mode.isDebugMode()) for (int i = 0; i < dexMethodTable.GetSize(); i++) 
		{
			System.out.print(dexTypeTable.GetTypeString(dexMethodTable.GetMethodClassIdx(i))+" ");
			System.out.print(dexStringTable.GetString(dexProtoTable.GetProtoShortyIdx(dexMethodTable.GetMethodProtoIdx(i)))+" ");
			System.out.println(dexStringTable.GetString(dexMethodTable.GetMethodNameIdx(i)));
		}

		for (int i = 0; i < dexClassDef.GetSize(); i++) 
		{
			String nameString = dexClassDef.GetClassDefItem(i).classNameString;
			if (Mode.isDebugMode()) System.out.println("LCLCLCLCLCLCLCLCLC!!!!!!!!!!");
			byte[] code = new byte[8];
			code[0] = 112;
			code[1] = 16;
			String superNameString = dexClassDef.GetClassDefItem(i).superClassNameString;
			if (Mode.isDebugMode()) System.out.println(nameString);
			if (Mode.isDebugMode()) System.out.println(superNameString);
			int methodidx = dexMethodTable.GetMethodIdxByInformation(superNameString , "<init>");
			code[2] = (byte)methodidx;
			methodidx = methodidx >>> 8;
			code[3] = (byte)methodidx;
			code[4] = 0;
			code[5] = 0;
			code[6] = 14;
			code[7] = 0;
			nameString = DeFixClassType(nameString);
			AddMethodCodeToMethod(nameString, "<init>", (short)1, (short)1, (short)1, 4, code);
		}
	}
	
	/**
	 * ����������Ϣ��������<br/>
	 * ���ⲿ��������ʱ�������������������Ϣ�����
	 */
	public void SerilizeAll()
	{
		Pretreatment();
		
		//ÿһ�ν�������
		dexStringTable.Serilize();
		dexTypeTable.Serilize();
		dexProtoTable.Serilize();
		dexFieldTable.Serilize();
		dexMethodTable.Serilize();
		dexClassDef.Serilize();
		
		AddInitToAllClass();
		
		if (Mode.IsOutputApk()) 
		{
			AddCodeToApkClasses();
		}
	}
	
	/**
	 * �������dex�ļ�<br/>
	 * ���ⲿ��������ʱ�����ڽ����з���������뷽��֮�����
	 */
	public void PrintDex()
	{
		pos = 0;
		PrintHeader();
		PrintStringTable();
		PrintTypeTable();
		PrintProtoTable();
		PrintFieldTable();
		PrintMethodTable();
		PrintClassDef();
		PrintAnnotationSetItems();
		PrintCodeItems();
		PrintAnnotationsDirectoryItems();
		PrintProtoItemTypeLists();
		PrintStringImages();
		PrintDebugInfo();
		PrintAnnotationOffItems();
		PrintSecrectCode();
		PrintStaticValueItems();
		PrintClassDataItem();
		PrintAlien();
		PrintMap();
		ReVerify();
		FlushBuffer();
	}
	
	/**
	 * �Բ����Ĵ洢�ڻ����е�dex�ļ������ɶ�Ӧ��checksum��SHA-1��֤<br/>
	 * ��������ȷ��dex�ļ�
	 */
	private void ReVerify()
	{
		dexReVerifier.DoReVerify(outBuffer);
	}
	
	/**
	 * ���Header���ֵ��������
	 */
	private void PrintHeader()
	{
		//Print MagicCode
		for (int i = 0; i < 8; i++) 
		{
			BytePrinter((byte) dexHeader.magicCode[i]);
		}
		
		//Print CheckSum
		//Need Backpatch
		for (int i = 0; i < 4; i++) 
		{
			BytePrinter((byte)0);
		}
		
		//Print SHA-1
		//Need Backpatch
		for (int i = 0; i < 20; i++) 
		{
			BytePrinter((byte)0);
		}
		
		//Print FileLength
		//Need Backpatch
		IntPrinter(0);
		
		//Print HeaderLength
		IntPrinter(112);
		
		//Print EndianTag
		for (int i = 0; i < 4; i++) 
		{
			BytePrinter(dexHeader.endianTag[i]);
		}
		
		//Print LinkSize = 0
		IntPrinter(0);
		
		//Print linkOff = 0
		IntPrinter(0);
		
		//Print MapOff
		//Need Backpatch
		IntPrinter(0);
		
		//Print StringSize
		IntPrinter(dexStringTable.GetSize());
		
		//Print StringOffset
		//Need Backpatch
		IntPrinter(0);
		
		//Print TypeSize
		IntPrinter(dexTypeTable.GetSize());
		
		//PrintTypeOffset
		//Need Backpatch
		IntPrinter(0);
		
		//PrintProtoSize
		IntPrinter(dexProtoTable.GetSize());
		
		//PrintProtoOffset
		//Need Backpatch
		IntPrinter(0);
		
		//PrintFieldSize
		IntPrinter(dexFieldTable.GetSize());
		
		//PrintFieldOffset
		//Need Backpatch
		IntPrinter(0);
		
		//PrintMethodSize
		IntPrinter(dexMethodTable.GetSize());
		
		//PrintMethodOffset
		//Need Backpatch
		IntPrinter(0);
		
		//Print ClassDefSize
		IntPrinter(dexClassDef.GetSize());

		//Print ClassDefOffset
		//Need Backpatch
		IntPrinter(0);
		
		//PrintDataSize
		//Need Backpatch
		IntPrinter(0);
		
		//PrintDataOffset
		//Need Backpatch
		IntPrinter(0);
	}
	
	/**
	 * ���StringTable���ֵ��������
	 */
	private void PrintStringTable()
	{
		dexHeader.stringIDsOffset = pos;
		ChangeInt(60, pos);
		for (int i = 0; i < dexStringTable.GetSize(); i++) 
		{
			dexStringTable.SetBackPatchOff(i, pos);
			IntPrinter(0);
		}
	}
	
	/**
	 * ���TypeTable���ֵ��������
	 */
	private void PrintTypeTable() 
	{
		dexHeader.typeIDsOffset = pos;
		ChangeInt(68, pos);
		for (int i = 0; i < dexTypeTable.GetSize(); i++) 
		{
			IntPrinter(dexTypeTable.GetTypeStringIdx(i));
		}
	}

	/**
	 * ���ProtoTable���ֵ��������
	 */
	private void PrintProtoTable() 
	{
		dexHeader.protoIDsOffset = pos;
		ChangeInt(76, pos);
		for (int i = 0; i < dexProtoTable.GetSize(); i++) 
		{
			IntPrinter(dexProtoTable.GetProtoShortyIdx(i));
			IntPrinter(dexProtoTable.GetProtoReturnTypeIdx(i));
			dexProtoTable.SetBackPatchOff(i, pos);
			IntPrinter(0);
		}
	}

	/**
	 * ���FieldTable���ֵ��������
	 */
	private void PrintFieldTable() 
	{
		dexHeader.fieldIDsOffset = pos;
		ChangeInt(84, pos);
		for (int i = 0; i < dexFieldTable.GetSize(); i++) 
		{
			ShortPrinter(dexFieldTable.GetFieldClassIdx(i));
			ShortPrinter(dexFieldTable.GetFieldTypeIdx(i));
			IntPrinter(dexFieldTable.GetFieldNameIdx(i));
		}
	}

	/**
	 * ���MethodTable���ֵ��������
	 */
	private void PrintMethodTable() 
	{
		dexHeader.methodIDsOffset = pos;
		ChangeInt(92, pos);
		for (int i = 0; i < dexMethodTable.GetSize(); i++) 
		{
			ShortPrinter(dexMethodTable.GetMethodClassIdx(i));
			ShortPrinter(dexMethodTable.GetMethodProtoIdx(i));
			IntPrinter(dexMethodTable.GetMethodNameIdx(i));
		}
	}

	/**
	 * ���ClassDef���ֵ��������
	 */
	private void PrintClassDef() 
	{
		dexHeader.classDefOffset = pos;
		ChangeInt(100, pos);
		for (int i = 0; i < dexClassDef.GetSize(); i++) 
		{
			IntPrinter(dexClassDef.GetClassDefItem(i).classIdx);
			IntPrinter(dexClassDef.GetClassDefItem(i).accessFlag);
			IntPrinter(dexClassDef.GetClassDefItem(i).superClassIdx);
			IntPrinter(dexClassDef.GetClassDefItem(i).interfaceOff);
			IntPrinter(dexClassDef.GetClassDefItem(i).sourceFileIdx);
			IntPrinter(dexClassDef.GetClassDefItem(i).annotationOff);
			dexClassDef.GetClassDefItem(i).SetClassDataOffBackPatchOffset(pos);
			IntPrinter(dexClassDef.GetClassDefItem(i).classDataOff);
			IntPrinter(dexClassDef.GetClassDefItem(i).staticValueOff);
		}
	}

	/**
	 * ���AnnotationSetItems���ֵ��������
	 */
	private void PrintAnnotationSetItems() 
	{
		dataBegin = pos;
		ChangeInt(108, pos);
		//nano�汾�д˴�Ϊ��
	}

	/**
	 * ���CodeItems���ֵ��������
	 */
	private void PrintCodeItems() 
	{
		while (pos%4 != 0) 
		{
			BytePrinter((byte)0);
		}
		dexHeader.codeItemOffset = pos;
		
		boolean isfirst = true;
		
		for (int i = 0; i < dexClassDef.GetSize(); i++) 
		{
			for (int j = 0; j < dexClassDef.GetClassDefItem(i).class_data_item.directMethodsSize; j++) 
			{
				if (isfirst) 
				{
					isfirst = false;
				}
				else 
				{
					while (pos%4 != 0) 
					{
						BytePrinter((byte)0);
					}
				}
				codeItemCount++;
				dexClassDef.GetClassDefItem(i).class_data_item.directMethods.get(j).codeOff = pos;
				ShortPrinter(dexClassDef.GetClassDefItem(i).class_data_item.directMethods.get(j).dexCode.registersSize);
				ShortPrinter(dexClassDef.GetClassDefItem(i).class_data_item.directMethods.get(j).dexCode.insSize);
				ShortPrinter(dexClassDef.GetClassDefItem(i).class_data_item.directMethods.get(j).dexCode.outsSize);
				ShortPrinter(dexClassDef.GetClassDefItem(i).class_data_item.directMethods.get(j).dexCode.triedSize);
				IntPrinter(dexClassDef.GetClassDefItem(i).class_data_item.directMethods.get(j).dexCode.debugInfoOff);
				IntPrinter(dexClassDef.GetClassDefItem(i).class_data_item.directMethods.get(j).dexCode.insnsSize);
				for (int k = 0; k < dexClassDef.GetClassDefItem(i).class_data_item.directMethods.get(j).dexCode.insnsSize*2; k++) 
				{
					BytePrinter(dexClassDef.GetClassDefItem(i).class_data_item.directMethods.get(j).dexCode.code[k]);
				}
			}
			for (int j = 0; j < dexClassDef.GetClassDefItem(i).class_data_item.virtualMethodsSize; j++) 
			{
				if (isfirst) 
				{
					isfirst = false;
				}
				else 
				{
					while (pos%4 != 0) 
					{
						BytePrinter((byte)0);
					}
				}
				codeItemCount++;
				dexClassDef.GetClassDefItem(i).class_data_item.virtualMethods.get(j).codeOff = pos;
				ShortPrinter(dexClassDef.GetClassDefItem(i).class_data_item.virtualMethods.get(j).dexCode.registersSize);
				ShortPrinter(dexClassDef.GetClassDefItem(i).class_data_item.virtualMethods.get(j).dexCode.insSize);
				ShortPrinter(dexClassDef.GetClassDefItem(i).class_data_item.virtualMethods.get(j).dexCode.outsSize);
				ShortPrinter(dexClassDef.GetClassDefItem(i).class_data_item.virtualMethods.get(j).dexCode.triedSize);
				IntPrinter(dexClassDef.GetClassDefItem(i).class_data_item.virtualMethods.get(j).dexCode.debugInfoOff);
				IntPrinter(dexClassDef.GetClassDefItem(i).class_data_item.virtualMethods.get(j).dexCode.insnsSize);
				for (int k = 0; k < dexClassDef.GetClassDefItem(i).class_data_item.virtualMethods.get(j).dexCode.insnsSize*2; k++) 
				{
					BytePrinter(dexClassDef.GetClassDefItem(i).class_data_item.virtualMethods.get(j).dexCode.code[k]);
				}
			}
		}
		
	}

	/**
	 * ���AnnotationsDirectoryItems���ֵ��������
	 */
	private void PrintAnnotationsDirectoryItems() 
	{
		//nano�汾�д˴�Ϊ��
	}

	/**
	 * ���ProtoItemTypeLists���ֵ��������
	 */
	private void PrintProtoItemTypeLists() 
	{
		while (pos%4 != 0) 
		{
			BytePrinter((byte)0);
		}
		dexHeader.typeListOffset = pos;
		int last = 0;
		for (int i = 0; i < dexProtoTable.GetSize(); i++) 
		{
			if(dexProtoTable.GetProtoParamsNum(i) != 0)
			{
				if (last == 1) 
				{
					ShortPrinter((short)0);
				}
				ChangeInt(dexProtoTable.GetProtoParamBackPatchOffset(i), pos);
				IntPrinter(dexProtoTable.GetProtoParamsNum(i));
				for (int j = 0; j < dexProtoTable.GetProtoParamsNum(i); j++) 
				{
					ShortPrinter(dexProtoTable.GetProtoParamTypeIdx(i, j));
				}
				if (dexProtoTable.GetProtoParamsNum(i) % 2 == 1) 
				{
					last = 1;
				}
				else 
				{
					last = 0;
				}
				typeListCount++;
			}
		}
	}

	/**
	 * ���StringImages���ֵ��������
	 */
	private void PrintStringImages() 
	{
		dexHeader.stringItemOffset = pos;
		for (int i = 0; i < dexStringTable.GetSize(); i++) 
		{
			ChangeInt(dexStringTable.GetBackPatchOff(i), pos);
			StringPrinter(dexStringTable.GetString(i));
		}
	}

	/**
	 * ���DebugInfo���ֵ��������
	 */
	private void PrintDebugInfo() 
	{
		//nano�汾�д˴�Ϊ��
	}

	/**
	 * ���AnnotationOffItems���ֵ��������
	 */
	private void PrintAnnotationOffItems() 
	{
		//nano�汾�д˴�Ϊ��
	}

	/**
	 * ���һ�����ش���
	 */
	private void PrintSecrectCode() 
	{
		//while (pos%4 != 0) 
		//{
		//	BytePrinter((byte)0);
		//}
	}
	
	/**
	 * ���StaticValueItems���ֵ��������
	 */
	private void PrintStaticValueItems() 
	{
		//nano�汾�д˴�Ϊ��
	}
	
	/**
	 * ���ClassDataItem���ֵ��������
	 */
	private void PrintClassDataItem() 
	{
		dexHeader.classdataItemOffset = pos;
		for (int i = 0; i < dexClassDef.GetSize(); i++) 
		{
			ChangeInt(dexClassDef.GetClassDefItem(i).GetClassDataOffBackPatchOffset(), pos);
			ULEB128Printer(dexClassDef.GetClassDefItem(i).class_data_item.staticFieldsSize);
			ULEB128Printer(dexClassDef.GetClassDefItem(i).class_data_item.instanceFieldsSize);
			ULEB128Printer(dexClassDef.GetClassDefItem(i).class_data_item.directMethodsSize);
			ULEB128Printer(dexClassDef.GetClassDefItem(i).class_data_item.virtualMethodsSize);
			
			for (int j = 0; j < dexClassDef.GetClassDefItem(i).class_data_item.staticFieldsSize; j++) 
			{
				ULEB128Printer(dexClassDef.GetClassDefItem(i).class_data_item.staticFields.get(j).fieldIdx);
				ULEB128Printer(dexClassDef.GetClassDefItem(i).class_data_item.staticFields.get(j).accessFlag);
			}
			for (int j = 0; j < dexClassDef.GetClassDefItem(i).class_data_item.instanceFieldsSize; j++) 
			{
				ULEB128Printer(dexClassDef.GetClassDefItem(i).class_data_item.instanceFields.get(j).fieldIdx);
				ULEB128Printer(dexClassDef.GetClassDefItem(i).class_data_item.instanceFields.get(j).accessFlag);
			}
			for (int j = 0; j < dexClassDef.GetClassDefItem(i).class_data_item.directMethodsSize; j++) 
			{
				ULEB128Printer(dexClassDef.GetClassDefItem(i).class_data_item.directMethods.get(j).methodIdx);
				ULEB128Printer(dexClassDef.GetClassDefItem(i).class_data_item.directMethods.get(j).accessFlag);
				ULEB128Printer(dexClassDef.GetClassDefItem(i).class_data_item.directMethods.get(j).codeOff);
			}
			for (int j = 0; j < dexClassDef.GetClassDefItem(i).class_data_item.virtualMethodsSize; j++) 
			{
				ULEB128Printer(dexClassDef.GetClassDefItem(i).class_data_item.virtualMethods.get(j).methodIdx);
				ULEB128Printer(dexClassDef.GetClassDefItem(i).class_data_item.virtualMethods.get(j).accessFlag);
				ULEB128Printer(dexClassDef.GetClassDefItem(i).class_data_item.virtualMethods.get(j).codeOff);
			}
		}
	}
	
	/**
	 * ���һ�������˰�Ĵ���
	 */
	private void PrintAlien() 
	{
		//while (pos%4 != 0) 
		//{
		//	BytePrinter((byte)0);
		//}
	}
	
	/**
	 * ���map��Ϣ
	 */
	private void PrintMap() 
	{
		while (pos%4 != 0) 
		{
			BytePrinter((byte)0);
		}
		dexHeader.mapOffset = pos;
		ChangeInt(52, pos);
		
		IntPrinter(12);
		
		ShortPrinter((short)0);
		ShortPrinter((short)0);
		IntPrinter(1);
		IntPrinter(0);
		
		ShortPrinter((short)1);
		ShortPrinter((short)0);
		IntPrinter(dexStringTable.GetSize());
		IntPrinter(dexHeader.stringIDsOffset);
		
		ShortPrinter((short)2);
		ShortPrinter((short)0);
		IntPrinter(dexTypeTable.GetSize());
		IntPrinter(dexHeader.typeIDsOffset);
		
		ShortPrinter((short)3);
		ShortPrinter((short)0);
		IntPrinter(dexProtoTable.GetSize());
		IntPrinter(dexHeader.protoIDsOffset);
		
		ShortPrinter((short)4);
		ShortPrinter((short)0);
		IntPrinter(dexFieldTable.GetSize());
		IntPrinter(dexHeader.fieldIDsOffset);
		
		ShortPrinter((short)5);
		ShortPrinter((short)0);
		IntPrinter(dexMethodTable.GetSize());
		IntPrinter(dexHeader.methodIDsOffset);
		
		ShortPrinter((short)6);
		ShortPrinter((short)0);
		IntPrinter(dexClassDef.GetSize());
		IntPrinter(dexHeader.classDefOffset);
		
		ShortPrinter((short)8193);
		ShortPrinter((short)0);
		IntPrinter(codeItemCount);
		IntPrinter(dexHeader.codeItemOffset);
		
		ShortPrinter((short)4097);
		ShortPrinter((short)0);
		IntPrinter(typeListCount);
		IntPrinter(dexHeader.typeListOffset);
		
		ShortPrinter((short)8194);
		ShortPrinter((short)0);
		IntPrinter(dexStringTable.GetSize());
		IntPrinter(dexHeader.stringItemOffset);
		
		ShortPrinter((short)8192);
		ShortPrinter((short)0);
		IntPrinter(dexClassDef.GetSize());
		IntPrinter(dexHeader.classdataItemOffset);
		
		ShortPrinter((short)4096);
		ShortPrinter((short)0);
		IntPrinter(1);
		IntPrinter(dexHeader.mapOffset);
		
		ChangeInt(32, pos);
		ChangeInt(104, pos - dataBegin);
	}
	
	/**
	 * �����������Ϣ������ļ�<br/>
	 * �����ֶ����ã��ڲ�����
	 */
	private void FlushBuffer() 
	{
		if (Mode.isDebugMode()) System.out.println("---Generated Dex Code---");
		byte[] tmp = new byte[1];
		for (int i = 0; i < outBuffer.size(); i++) 
		{
			tmp[0] = outBuffer.get(i);
			int ans = 255 & outBuffer.get(i);
			if (Mode.isDebugMode()) System.out.print(Integer.toHexString(ans) + " ");
			if (Mode.isDebugMode()) if ((i+1)%8 == 0) {
				System.out.println();
			}
			try {
				target.write(tmp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ���ߺ���<br/>
	 * ��һ��byte������ļ���������ǰλ��
	 * @param src Ҫ�����byte
	 */
	private void BytePrinter(byte src)
	{
		byte[] tmp = new byte[1];
		tmp[0] = src;
		for (int i = 0; i < tmp.length; i++) 
		{
			outBuffer.add(tmp[i]);
			pos += 1;
		}
	}
	
	/**
	 * ���ߺ���<br/>
	 * ��һ��short������ļ���������ǰλ��
	 * @param src Ҫ�����short
	 */
	private void ShortPrinter(short src)
	{
		byte[] tmp = new byte[2];
		tmp[0] = (byte)src;
		tmp[1] = (byte)(src>>>8);
		for (int i = 0; i < tmp.length; i++) 
		{
			outBuffer.add(tmp[i]);
			pos += 1;
		}
	}
	
	/**
	 * ���ߺ���<br/>
	 * ��һ��int������ļ���������ǰλ��
	 * @param src Ҫ�����int
	 */
	private void IntPrinter(int src)
	{
		byte[] tmp = new byte[4];
		tmp[0] = (byte)src;
		tmp[1] = (byte)(src>>>8);
		tmp[2] = (byte)(src>>>16);
		tmp[3] = (byte)(src>>>24);
		for (int i = 0; i < tmp.length; i++) 
		{
			outBuffer.add(tmp[i]);
			pos += 1;
		}
	}
	
	/**
	 * ���ߺ���<br/>
	 * �޸�dex�����У�λ��offsetλ�õ�һ��int��ֵ
	 * @param offset Ҫ�޸ĵ�����λ��
	 * @param src Ҫ�޸ĳɵ���ֵ
	 */
	private void ChangeInt(int offset, int src)
	{
		byte[] tmp = new byte[4];
		tmp[0] = (byte)src;
		tmp[1] = (byte)(src>>>8);
		tmp[2] = (byte)(src>>>16);
		tmp[3] = (byte)(src>>>24);
		for (int i = 0; i < tmp.length; i++) 
		{
			outBuffer.set(offset+i, tmp[i]);
		}
	}
	
	/**
	 * ���ߺ���<br/>
	 * ��һ��������ULEB128����ʽ������ļ����嵱ǰλ��
	 * @param src Ҫ���������ֵ
	 */
	private void ULEB128Printer(int src)
	{
		byte[] tmp = new byte[5];
		int high1one = -128;
		int low7one = 127;
		int tmpsrc = src;
		int size = 0;
		int srclow7 = tmpsrc & low7one;
		
		tmp[0] = (byte)srclow7;
		size++;
		tmpsrc = tmpsrc >>> 7;
		
		while (tmpsrc != 0) 
		{
			tmp[size-1] = (byte) (tmp[size -1] | high1one);
			srclow7 = tmpsrc & low7one;
			tmp[size] = (byte)srclow7;
			size++;
			tmpsrc = tmpsrc >>> 7;
		}
		
		for (int i = 0; i < size; i++) 
		{
			outBuffer.add(tmp[i]);
			pos += 1;
		}
	}
	
	/**
	 * ���ߺ���<br/>
	 * ��һ���ַ����������dex�ļ������еĵ�ǰλ��
	 * @param src Ҫ������ַ���
	 */
	private void StringPrinter(String src)
	{
		int size = src.length();
		byte Word;
		ULEB128Printer(size);
		for (int i = 0; i < size; i++) 
		{
			Word = (byte)src.charAt(i);
			BytePrinter(Word);
		}
		BytePrinter((byte)0);
	}
	
	/**
	 * ���ߺ���<br/>
	 * ������������ʵ�ʴ洢���ƽ���ת��
	 * @param _type ��������������ַ���
	 * @return ʵ�ʴ洢�����������ַ���
	 */
	private String FixClassType(String _type)
	{
		String retString;
		if (_type.equals("int")) return "I";
		if (_type.equals("boolean")) return "Z";
		if (_type.equals("void")) return "V";
		if (_type.equals("int[]")) return "[I";
		if (_type.equals("String[]")) return "[Ljava/lang/String;";
 		if (_type.length() > 1)
		{
			if (_type.startsWith("[")) 
			{
				if (_type.length() > 2) 
				{
					retString = new String("[L" + _type.substring(1) + ";" );
					return retString;
				}
				retString = _type;
				return retString;
			}
			retString = new String("L" + _type + ";");
			return retString;
		}
 		else
 		{
 			if (!_type.equals("I") && !_type.equals("Z") && !_type.equals("V")) 
 			{
 				retString = new String("L" + _type + ";");
 				return retString;
			}
 		}
		retString = _type;
		return retString;
	}

	/**
	 * ���ߺ���<br/>
	 * ��ǰ����������ε������ַ�����ԭ��ԭ��������
	 * @param _fixtype ǰ����������ε������ַ���
	 * @return ����ԭ�����ӵ������ַ���
	 */
	private String DeFixClassType(String _fixtype)
	{
		String retString;
		if (_fixtype.startsWith("L")) 
		{
			retString = new String(_fixtype.substring(1, _fixtype.length()-1));
			return retString;
		}
		if (_fixtype.startsWith("[L")) 
		{
			retString = new String("[" + _fixtype.substring(2, _fixtype.length()-1));
			return retString;
		}
		retString = new String(_fixtype);
		return retString;
	}
	
	/**
	 * ���ߺ���<br/>
	 * ����·����.ת����/���Է��ϴ洢Ҫ��
	 * @param _src ԭʼ�ַ���
	 * @return ת����Ķ��ַ���
	 */
	private String FixPoint(String _src)
	{
		return _src.replace('.', '/');
	}

}
