package minijava.dexstruct.items;

/**
 * MethodItem�����ݽṹ
 */
public class MethodItem
{
	public short classIdx;	//to type table  the class it belongs to
	public short protoIdx;
	public int nameIdx;
	public String className;
	public String protoShorty;
	public String methodName;
	public String modifiedShorty;
	public String modifiedMethod;
	//String protoReturnTypeString;
	//int paramNum;
	//String[] paramStrings;
	/**
	 * ���캯�� ��Ҫ������Ϣ
	 * @param _className ��������
	 * @param _methodName ������
	 * @param _protoShorty proto��shorty�ֶ���
	 * @param _modifiedShorty ���������shorty�ַ���
	 * @param _modifiedMethod ��������ķ�����Ϣ
	 */
	public MethodItem(String _className, String _methodName, String _protoShorty, String _modifiedShorty, String _modifiedMethod)
	{
		className = _className;
		methodName = _methodName;
		protoShorty = _protoShorty;
		modifiedShorty = _modifiedShorty;
		modifiedMethod = _modifiedMethod;
	}
}