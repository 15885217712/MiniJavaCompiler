package minijava.dexstruct.items;

/**
 * EncodedMethod�����ݽṹ
 */
public class EncodedMethod
{
	public int methodIdx;
	public int accessFlag;
	public int codeOff; //֮����
	public CodeItem dexCode;
	
	public String modifiedMethod; //�������� methodIdx
	
	/**
	 * ���캯��
	 * @param _className ��������
	 * @param _methodName ������
	 * @param _accessFlag ����Ȩ��
	 */
	public EncodedMethod(String _className, String _methodName, int _accessFlag)//, short _registersSize, short _insSize, short _outsSize, int _insnsSize, byte[] _code
	{
		modifiedMethod = new String(_className + _methodName);
		accessFlag = _accessFlag;
	}
}