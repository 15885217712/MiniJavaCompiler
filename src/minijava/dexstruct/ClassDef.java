package minijava.dexstruct;

import java.util.*;

import util.AccessFlag;
import minijava.dexstruct.items.*;

/**
 * ClassDef�ε����ݴ洢�ṹ
 */
public class ClassDef {
	private int size;
	private ArrayList<ClassDefItem> aClassDefItems;
	private HashSet<String> hClassNameSet;
	
	private HashMap<String, Integer> hTmpClassNameToArrayIdxMap;
	
	private HashMap<String, Integer> hClassNameToClassDefItemIdx;
	private HashMap<Integer, String> hClassDefItemIdxToClassName;
	
	private DexStringTable stringTable;
	private DexTypeTable typeTable;
	private DexFieldTable fieldTable;
	private DexMethodTable methodTable;
	
	/**
	 * ���캯��
	 * Ҫ����ص�table��������Ϊ�������룬Ҫ�õ������Ϣ
	 * @param _StringTable
	 * @param _TypeTable
	 * @param _FieldTable
	 * @param _MethodTable
	 */
	public ClassDef(DexStringTable _StringTable, DexTypeTable _TypeTable, DexFieldTable _FieldTable, DexMethodTable _MethodTable) 
	{
		stringTable = _StringTable;
		typeTable = _TypeTable;
		fieldTable = _FieldTable;
		methodTable = _MethodTable;
		aClassDefItems = new ArrayList<ClassDefItem>();
		hClassNameSet = new HashSet<String>();
		hTmpClassNameToArrayIdxMap = new HashMap<String, Integer>();
		hClassDefItemIdxToClassName = new HashMap<Integer, String>();
		hClassNameToClassDefItemIdx = new HashMap<String, Integer>();
	}
	
	/**
	 * ע��һ����
	 * @param _className �����ƣ���L����
	 * @param _superClassName �������ƣ���L����
	 */
	public void AddClass(String _className, String _superClassName, String _srcFileName)
	{
		if (hClassNameSet.add(_className)) 
		{
			hTmpClassNameToArrayIdxMap.put(_className, aClassDefItems.size());
			//�˴��ĳ��������඼�� public
			aClassDefItems.add(new ClassDefItem(_srcFileName, _className, _superClassName, 1));
			typeTable.AddType(_className);
			typeTable.AddType(_superClassName);
			stringTable.AddString(_srcFileName);
		}
	}
	
	/**
	 * ע��һ���࣬����GUI��֧������Ϣ
	 * @param _className �����ƣ���L����
	 * @param _superClassName ��������
	 * @param _srcFileName ��Դ�ļ�����
	 * @param _accessFlag ����Ȩ��
	 */
	public void LCAddClass(String _className, String _superClassName, String _srcFileName, int _accessFlag)
	{
		if (hClassNameSet.add(_className)) 
		{
			hTmpClassNameToArrayIdxMap.put(_className, aClassDefItems.size());
			//�˴��ĳ��������඼�� public
			aClassDefItems.add(new ClassDefItem(_srcFileName, _className, _superClassName, _accessFlag));
			typeTable.AddType(_className);
			typeTable.AddType(_superClassName);
			stringTable.AddString(_srcFileName);
		}
	}
	
	/**
	 * ��һ���������һ����̬��Ա����
	 * @param _className �����ƣ���L����
	 * @param _fieldType ��Ա������������
	 * @param _fieldName ��Ա��������
	 */
	public void AddStaticFieldToClass(String _className, String _fieldType, String _fieldName)
	{
		String modField = new String(_className + _fieldType + _fieldName);
		int classIdx = hTmpClassNameToArrayIdxMap.get(_className);
		if (aClassDefItems.get(classIdx).class_data_item.hModifiedFieldSet.add(modField)) 
		{
			fieldTable.AddField(_className, _fieldType, _fieldName);
			aClassDefItems.get(classIdx).class_data_item.staticFields.add(new EncodedField(_className, _fieldType, _fieldName, AccessFlag.STATIC));
		}
	}

	/**
	 * ��һ���������һ����̬��Ա���ԣ�����GUI֧�ֵ���Ϣ
	 * @param _className �����ƣ���L����
	 * @param _fieldType ��Ա������������
	 * @param _fieldName ��Ա��������
	 * @param _accessFlag ��̬��Ա���Եķ���Ȩ��
	 */
	public void LCAddStaticFieldToClass(String _className, String _fieldType, String _fieldName, int _accessFlag)
	{
		String modField = new String(_className + _fieldType + _fieldName);
		int classIdx = hTmpClassNameToArrayIdxMap.get(_className);
		if (aClassDefItems.get(classIdx).class_data_item.hModifiedFieldSet.add(modField)) 
		{
			fieldTable.AddField(_className, _fieldType, _fieldName);
			aClassDefItems.get(classIdx).class_data_item.staticFields.add(new EncodedField(_className, _fieldType, _fieldName, _accessFlag));
		}
	}
	
	/**
	 * ��һ���������һ���Ǿ�̬��Ա����
	 * @param _className �����ƣ���L����
	 * @param _fieldType ��Ա������������
	 * @param _fieldName ��Ա��������
	 */
	public void AddInstanceFieldToClass(String _className, String _fieldType, String _fieldName)
	{
		String modField = new String(_className + _fieldType + _fieldName);
		int classIdx = hTmpClassNameToArrayIdxMap.get(_className);
		if (aClassDefItems.get(classIdx).class_data_item.hModifiedFieldSet.add(modField)) 
		{
			fieldTable.AddField(_className, _fieldType, _fieldName);
			aClassDefItems.get(classIdx).class_data_item.instanceFields.add(new EncodedField(_className, _fieldType, _fieldName, 0));
		}
	}
	
	/**
	 * ��һ���������һ����̬����
	 * @param _className �����ƣ���L
	 * @param _methodName ��������
	 * @param _returnType ��������ֵ��������
	 * @param _accessFlag ����Ȩ�ޣ���ʹ��lctool�е�AccessFlag�е�static
	 * @param _paramNum ��������
	 * @param _params ���������б�
	 * @param _registersSize ʹ�õļĴ�������
	 * @param _insSize ����ļĴ�������
	 * @param _outsSize ���õļĴ�������
	 * @param _insnsSize ���볤�ȣ���2Byte�ƣ�
	 * @param _code ����
	 */
	public void AddDirectMethodToClass(String _className, String _methodName, String _returnType, int _accessFlag,
			int _paramNum, String[] _params)//, short _registersSize, short _insSize, short _outsSize, int _insnsSize, byte[] _code
	{
		String modifiedMethod = new String(_className + _methodName);
		int classIdx = hTmpClassNameToArrayIdxMap.get(_className);
		if (aClassDefItems.get(classIdx).class_data_item.hModifiedMethodSet.add(modifiedMethod)) 
		{
			methodTable.AddMethod(_className, _methodName, _returnType, _paramNum, _params);
			aClassDefItems.get(classIdx).class_data_item.directMethods.add(new EncodedMethod(_className, 
					_methodName, _accessFlag));//, _registersSize, _insSize, _outsSize, _insnsSize, _code
		}
	}
	
	/**
	 * ��һ���������һ����̬����
	 * @param _className �����ƣ���L
	 * @param _methodName ��������
	 * @param _returnType ��������ֵ��������
	 * @param _accessFlag ����Ȩ��
	 * @param _paramNum ��������
	 * @param _params ���������б�
	 * @param _registersSize ʹ�õļĴ�������
	 * @param _insSize ����ļĴ�������
	 * @param _outsSize ���õļĴ�������
	 * @param _insnsSize ���볤�ȣ���2Byte�ƣ�
	 * @param _code ��������
	 */
	public void AddVirtualMethodToClass(String _className, String _methodName, String _returnType, int _accessFlag,
			int _paramNum, String[] _params)//, short _registersSize, short _insSize, short _outsSize, int _insnsSize, byte[] _code
	{
		String modifiedMethod = new String(_className + _methodName);
		int classIdx = hTmpClassNameToArrayIdxMap.get(_className);
		if (aClassDefItems.get(classIdx).class_data_item.hModifiedMethodSet.add(modifiedMethod)) 
		{
			methodTable.AddMethod(_className, _methodName, _returnType, _paramNum, _params);
			aClassDefItems.get(classIdx).class_data_item.virtualMethods.add(new EncodedMethod(_className, 
					_methodName, _accessFlag));
		}
	}
		
	/**
	 * ��һ����������Ӷ����ƴ���<br/>
	 * ��serilize֮����
	 * @param _className ��Ӧ������
	 * @param _methodName ��Ӧ��������
	 * @param _registersSize ʹ�üĴ�������
	 * @param _insSize ����ļĴ���������
	 * @param _outsSize ���õļĴ�������
	 * @param _insnsSize ���볤�ȣ���2Byte�ƣ�
	 * @param _code ��������
	 */
	public void AddMethodCodeToMethod(String _className, String _methodName, 
			short _registersSize, short _insSize, short _outsSize, int _insnsSize, byte[] _code)
	{
		int classidx = hClassNameToClassDefItemIdx.get(_className);
		String modMethodString = new String(_className + _methodName);
		for (int i = 0; i < aClassDefItems.get(classidx).class_data_item.directMethodsSize; i++) 
		{
			if (aClassDefItems.get(classidx).class_data_item.directMethods.get(i).modifiedMethod.equals(modMethodString)) 
			{
				aClassDefItems.get(classidx).class_data_item.directMethods.get(i).dexCode
				= new CodeItem(_registersSize, _insSize, _outsSize, _insnsSize, _code);
				return;
			}
		}
		for (int i = 0; i < aClassDefItems.get(classidx).class_data_item.virtualMethodsSize; i++) 
		{
			if (aClassDefItems.get(classidx).class_data_item.virtualMethods.get(i).modifiedMethod.equals(modMethodString)) 
			{
				aClassDefItems.get(classidx).class_data_item.virtualMethods.get(i).dexCode
				= new CodeItem(_registersSize, _insSize, _outsSize, _insnsSize, _code);
				return;
			}
		}
	}
	
	/**
	 * ������������table������ Serilize() ֮�������
	 */
	public void Serilize()
	{
		for (int i = 0; i < aClassDefItems.size(); i++) 
		{
			aClassDefItems.get(i).classIdx = typeTable.GetTypeIdx(aClassDefItems.get(i).classNameString);
			aClassDefItems.get(i).superClassIdx = typeTable.GetTypeIdx(aClassDefItems.get(i).superClassNameString);
			aClassDefItems.get(i).sourceFileIdx = stringTable.GetStringIdx(aClassDefItems.get(i).sourceFileNameString);
			
			aClassDefItems.get(i).class_data_item.staticFieldsSize = aClassDefItems.get(i).class_data_item.staticFields.size();
			aClassDefItems.get(i).class_data_item.instanceFieldsSize = aClassDefItems.get(i).class_data_item.instanceFields.size();
			aClassDefItems.get(i).class_data_item.directMethodsSize = aClassDefItems.get(i).class_data_item.directMethods.size();
			aClassDefItems.get(i).class_data_item.virtualMethodsSize = aClassDefItems.get(i).class_data_item.virtualMethods.size();
			
			for (int j = 0; j < aClassDefItems.get(i).class_data_item.staticFieldsSize; j++) 
			{
				aClassDefItems.get(i).class_data_item.staticFields.get(j).fieldIdx = 
						fieldTable.GetFieldIdxByModifiedField(aClassDefItems.get(i).class_data_item.staticFields.get(j).modifiedField);
			}
			for (int j = 0; j < aClassDefItems.get(i).class_data_item.staticFieldsSize-1; j++) 
			{
				for (int k = j; k < aClassDefItems.get(i).class_data_item.staticFieldsSize; k++) 
				{
					if (aClassDefItems.get(i).class_data_item.staticFields.get(j).fieldIdx > aClassDefItems.get(i).class_data_item.staticFields.get(k).fieldIdx) 
					{
						EncodedField tmpEncodedField = aClassDefItems.get(i).class_data_item.staticFields.get(j);
						aClassDefItems.get(i).class_data_item.staticFields.set(j, aClassDefItems.get(i).class_data_item.staticFields.get(k));
						aClassDefItems.get(i).class_data_item.staticFields.set(k, tmpEncodedField);
					}
				}
			}
			for (int j = aClassDefItems.get(i).class_data_item.staticFieldsSize-1 ; j > 0 ; j--) 
			{
				aClassDefItems.get(i).class_data_item.staticFields.get(j).fieldIdx -= aClassDefItems.get(i).class_data_item.staticFields.get(j-1).fieldIdx;
			}
			
			for (int j = 0; j < aClassDefItems.get(i).class_data_item.instanceFieldsSize; j++) 
			{
				aClassDefItems.get(i).class_data_item.instanceFields.get(j).fieldIdx = 
						fieldTable.GetFieldIdxByModifiedField(aClassDefItems.get(i).class_data_item.instanceFields.get(j).modifiedField);
			}
			for (int j = 0; j < aClassDefItems.get(i).class_data_item.instanceFieldsSize-1; j++) 
			{
				for (int k = j; k < aClassDefItems.get(i).class_data_item.instanceFieldsSize; k++) 
				{
					if (aClassDefItems.get(i).class_data_item.instanceFields.get(j).fieldIdx > aClassDefItems.get(i).class_data_item.instanceFields.get(k).fieldIdx) 
					{
						EncodedField tmpEncodedField = aClassDefItems.get(i).class_data_item.instanceFields.get(j);
						aClassDefItems.get(i).class_data_item.instanceFields.set(j, aClassDefItems.get(i).class_data_item.instanceFields.get(k));
						aClassDefItems.get(i).class_data_item.instanceFields.set(k, tmpEncodedField);
					}
				}
			}
			for (int j = aClassDefItems.get(i).class_data_item.instanceFieldsSize-1 ; j > 0 ; j--) 
			{
				aClassDefItems.get(i).class_data_item.instanceFields.get(j).fieldIdx -= aClassDefItems.get(i).class_data_item.instanceFields.get(j-1).fieldIdx;
			}
			
			for (int j = 0; j < aClassDefItems.get(i).class_data_item.directMethodsSize; j++) 
			{
				aClassDefItems.get(i).class_data_item.directMethods.get(j).methodIdx =
						methodTable.GetMethodIdxByModifiedMethod(aClassDefItems.get(i).class_data_item.directMethods.get(j).modifiedMethod);
			}
			for (int j = 0; j < aClassDefItems.get(i).class_data_item.directMethodsSize-1; j++) 
			{
				for (int k = j; k < aClassDefItems.get(i).class_data_item.directMethodsSize; k++) 
				{
					if (aClassDefItems.get(i).class_data_item.directMethods.get(j).methodIdx > aClassDefItems.get(i).class_data_item.directMethods.get(k).methodIdx) 
					{
						EncodedMethod tmpEncodedMethod = aClassDefItems.get(i).class_data_item.directMethods.get(j);
						aClassDefItems.get(i).class_data_item.directMethods.set(j, aClassDefItems.get(i).class_data_item.directMethods.get(k));
						aClassDefItems.get(i).class_data_item.directMethods.set(k, tmpEncodedMethod);
					}
				}
			}
			for (int j = aClassDefItems.get(i).class_data_item.directMethodsSize-1 ; j > 0 ; j--) 
			{
				aClassDefItems.get(i).class_data_item.directMethods.get(j).methodIdx -= aClassDefItems.get(i).class_data_item.directMethods.get(j-1).methodIdx;
			}
			
			for (int j = 0; j < aClassDefItems.get(i).class_data_item.virtualMethodsSize; j++) 
			{
				aClassDefItems.get(i).class_data_item.virtualMethods.get(j).methodIdx =
						methodTable.GetMethodIdxByModifiedMethod(aClassDefItems.get(i).class_data_item.virtualMethods.get(j).modifiedMethod);
			}
			for (int j = 0; j < aClassDefItems.get(i).class_data_item.virtualMethodsSize-1; j++) 
			{
				for (int k = j; k < aClassDefItems.get(i).class_data_item.virtualMethodsSize; k++) 
				{
					if (aClassDefItems.get(i).class_data_item.virtualMethods.get(j).methodIdx > aClassDefItems.get(i).class_data_item.virtualMethods.get(k).methodIdx) 
					{
						EncodedMethod tmpEncodedMethod = aClassDefItems.get(i).class_data_item.virtualMethods.get(j);
						aClassDefItems.get(i).class_data_item.virtualMethods.set(j, aClassDefItems.get(i).class_data_item.virtualMethods.get(k));
						aClassDefItems.get(i).class_data_item.virtualMethods.set(k, tmpEncodedMethod);
					}
				}
			}
			for (int j = aClassDefItems.get(i).class_data_item.virtualMethodsSize-1 ; j > 0 ; j--) 
			{
				aClassDefItems.get(i).class_data_item.virtualMethods.get(j).methodIdx -= aClassDefItems.get(i).class_data_item.virtualMethods.get(j-1).methodIdx;
			}
		}
		
		//����  Ҫ����������֮ǰ
		ArrayList<ClassNode> classNodes = new ArrayList<ClassDef.ClassNode>();
		HashMap<Integer, Integer> classIdxToArrayIdxMap = new HashMap<Integer, Integer>();
		int objIdx = typeTable.GetTypeIdx("Ljava/lang/Object;");
		int actIdx = typeTable.GetTypeIdx("Landroid/app/Activity;");
		for (int i = 0; i < aClassDefItems.size(); i++) 
		{
			classNodes.add(new ClassNode(aClassDefItems.get(i).classIdx, aClassDefItems.get(i).superClassIdx));
			classIdxToArrayIdxMap.put(aClassDefItems.get(i).classIdx, i);
		}
		for (int i = 0; i < classNodes.size(); i++) 
		{
			if (classNodes.get(i).fatheridx != objIdx && classNodes.get(i).fatheridx != actIdx) 
			{
				classNodes.get(i).SetFatherNode(classNodes.get(classIdxToArrayIdxMap.get(classNodes.get(i).fatheridx)));			
			}
		}
		ArrayList<Integer> sortRank = new ArrayList<Integer>();
		ArrayList<ClassNode> nowFatherArrayList;
		ArrayList<ClassNode> nextFatherArrayList;
		nowFatherArrayList = new ArrayList<ClassDef.ClassNode>();
		int last = classNodes.size();
		for (int i = 0; i < classNodes.size(); i++) 
		{
			if (classNodes.get(i).fatheridx == objIdx || classNodes.get(i).fatheridx == actIdx) 
			{
				nowFatherArrayList.add(classNodes.get(i));
			}
		}
		while (true) 
		{
			nextFatherArrayList = new ArrayList<ClassDef.ClassNode>();
			ArrayList<Integer> tmpSortRank = new ArrayList<Integer>();
			for (int i = 0; i < nowFatherArrayList.size(); i++) 
			{
				tmpSortRank.add(nowFatherArrayList.get(i).classidx);
				for (int j = 0; j < nowFatherArrayList.get(i).sonNodes.size(); j++) 
				{
					nextFatherArrayList.add(nowFatherArrayList.get(i).sonNodes.get(j));
				}
				last--;
			}
			for (int i = 0; i < tmpSortRank.size()-1; i++) 
			{
				for (int j = i; j < tmpSortRank.size(); j++) 
				{
					if (tmpSortRank.get(i) > tmpSortRank.get(j)) 
					{
						int t = tmpSortRank.get(i);
						tmpSortRank.set(i, tmpSortRank.get(j));
						tmpSortRank.set(j, t);
					}
				}
			}
			for (int i = 0; i < tmpSortRank.size(); i++) 
			{
				sortRank.add(tmpSortRank.get(i));
			}
			if (last == 0) 
			{
				break;
			}
			nowFatherArrayList = nextFatherArrayList;
		}
		for (int i = 0; i < sortRank.size(); i++) 
		{
			int tmp = classIdxToArrayIdxMap.get(sortRank.get(i));
			sortRank.set(i, tmp);
		}
		ArrayList<ClassDefItem> tmpItems = new ArrayList<ClassDefItem>();
		for (int i = 0; i < aClassDefItems.size(); i++) 
		{
			tmpItems.add(aClassDefItems.get(i));
		}
		for (int i = 0; i < aClassDefItems.size(); i++) 
		{
			aClassDefItems.set(i, tmpItems.get(sortRank.get(i)));
			//System.out.println(aClassDefItems.get(i).classNameString +" father:"+ aClassDefItems.get(i).superClassNameString);
		}
				
		for (int i = 0; i < aClassDefItems.size(); i++) 
		{
			hClassDefItemIdxToClassName.put(i, aClassDefItems.get(i).classNameString);
			hClassNameToClassDefItemIdx.put(aClassDefItems.get(i).classNameString, i);
		}
		
		size = aClassDefItems.size();
	}
	
	/**
	 * ������Ŀ����
	 */
	public void ReFreshSize()
	{
		size = aClassDefItems.size();
	}
	
	/**
	 * ��ȡ��Ŀ����
	 * @return ����ֵΪ������Ŀ����
	 */
	public int GetSize()
	{
		return size;
	}
	
	/**
	 * ͨ��index���ض�Ӧ��classdef��Ŀ
	 * @param _classdefitemidx Ҫ���ص�index
	 * @return ����һ��classdef��Ŀ
	 */
	public ClassDefItem GetClassDefItem(int _classdefitemidx)
	{
		return aClassDefItems.get(_classdefitemidx);
	}
		
	/**
	 * �ڲ���
	 * ������Ŀ������ʱ����ʹ��
	 */
	private class ClassNode
	{
		int classidx;
		int fatheridx;
		ClassNode fatherNode;
		ArrayList<ClassNode> sonNodes;
		public ClassNode(int _classidx, int _fatheridx) 
		{
			classidx = _classidx;
			fatheridx = _fatheridx;
			sonNodes = new ArrayList<ClassDef.ClassNode>();
		}
		void SetFatherNode(ClassNode _fathernode)
		{
			fatherNode = _fathernode;
			fatherNode.sonNodes.add(this);
		}
	}
}