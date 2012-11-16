package minijava.dexstruct.items;

/**
 * ClassDefItem�����ݽṹ
 */
public class ClassDefItem
{	
	public int classIdx;		//typetable
	public int accessFlag;
	public int superClassIdx;	//typetable
	public int interfaceOff = 0;
	public int sourceFileIdx = 0;
	public int annotationOff = 0;
	public int classDataOff = 0;
	public int staticValueOff = 0;
	public ClassDataItem class_data_item;
	
	public String classNameString;
	public String superClassNameString;
	public String sourceFileNameString;
	
	/**
	 * ���캯������Ҫ������Ϣ����������
	 * @param _srcFile ����Դ�ļ���
	 * @param _className ����
	 * @param _superClassName ������
	 * @param _accessFlag ����Ȩ��
	 */
	public ClassDefItem(String _srcFile, String _className, String _superClassName, int _accessFlag)
	{
		classNameString = new String(_className);
		superClassNameString = new String(_superClassName);
		sourceFileNameString = new String(_srcFile);
		class_data_item = new ClassDataItem();
		accessFlag = _accessFlag;
	}
	
	/**
	 * ���û���offset��Ϣ
	 * @param _backPatchOff ����offset
	 */
	public void SetClassDataOffBackPatchOffset(int _backPatchOff)
	{
		class_data_item.classDataOffBackPatchOffset = _backPatchOff;
	}
	
	/**
	 * ��ȡ����offset��Ϣ
	 * @return ����offset
	 */
	public int GetClassDataOffBackPatchOffset()
	{
		return class_data_item.classDataOffBackPatchOffset;
	}
}