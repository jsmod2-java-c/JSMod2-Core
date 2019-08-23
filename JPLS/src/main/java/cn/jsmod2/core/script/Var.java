package cn.jsmod2.core.script;

import cn.jsmod2.core.ex.TypeErrorException;



import static cn.jsmod2.core.script.EmeraldScriptVM.*;


public class Var extends Memory{

    private Object value;

    private String type;

    private boolean isNull;

    private String name;

    private boolean readonly;

    private Var(String name,Object value){
        this.name = name;
        this.value = value;
        this.type = parseType(value);
        if(value.toString().matches("NULL")){
            isNull = true;
        }
    }

    private String parseType(Object value){
        if(value.toString().matches("[0-9]+")){
            return "INT";
        }else if(value.toString().matches("[0-9]+\\.[0-9]+")){
            return "DOUBLE";
        }else if(value.toString().matches("true|false")){
            return "BOOL";
        }else if(value.toString().matches("NULL")){
            return "NULL";
        }else if(value.toString().matches("'([\\s\\S]+|)'")){
            return "STRING";
        }else{
            String val = value.getClass().getName();
            if(val.equals("java.lang.String")){
                return "STRING";
            }
            return val;
        }
    }

    public void setPointerType(String type){
        if(type.matches("INT|NULL")){
            this.type = "*";
        }else if(type.matches("\\*+")){
            this.type = type+"*";
        }
    }

    public String getType() {
        return type;
    }

    public Object getObject(){
        return value;
    }

    public void setObject(Object value){
        this.value = value;
        this.type = parseType(value);
    }

    public String getValue() {
        return value.toString();
    }

    public boolean isNull() {
        return isNull;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        if(readonly){
            throw new TypeErrorException("the value is readonly");
        }
        if(type.matches("\\*+")){
            if(!value.startsWith("&")){
                Var var;
                try{
                    var = (Var) (getVM().getMemory_address_mapping().get(Integer.parseInt(value)));
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    throw new TypeErrorException("the pointer have already inited");
                }

                if(!var.getType().equals(type)){
                    this.value = value;
                }else{
                    throw new TypeErrorException("the value type is not "+type);
                }
            }else{
                this.value = getVM().getVars().get(value.substring(value.indexOf("&")+1)).hashCode()+"";
            }
            return;
        }
        String type = parseType(value);
        if(getType().equals("NULL")||getType().equals("UNKNOWN")){
            this.value = value;
            this.type = type;
            return;
        }
        if(type.equals(getType())){
            this.value = value;
        }else{
            throw new TypeErrorException("the type is error value is "+getType()+",but the new value type is "+type);
        }
    }

    public String getName() {
        return name;
    }

    public void unset(){
        if(readonly){
            throw new TypeErrorException("the type is readonly");
        }
        this.type = "NULL";
        this.value = "NULL";
    }
    // var c=1
    public static Var compile(String command){
        if(command.matches(matches.get("ptr"))){
            String[] right_left = command.split(":\\*");
            String right = right_left[0];
            String left = right_left[1];
            Var var;
            if(left.startsWith("d:")){
                var = (Var) (getVM().getMemory_address_mapping().get(Integer.parseInt(left.replaceAll("(d:)+",""))));
            }else{
                var = getVM().getVars().get(left);
            }
            Var var_ptr = new Var(right,var.hashCode()+"");
            var_ptr.setPointerType(var.getType());
            if(command.matches("(global::)*(const )[a-z0-9A-Z_]+:\\*[\\s\\S]+")){
                var_ptr.readonly = true;
                var_ptr.setName(right.split(" ")[1]);
            }
            return var_ptr;
        }else{
            String[] key_value = command.split("=");
            if(key_value.length<2){
                command = command+" ";
                String key = key_value[0];
                key_value = new String[2];
                key_value[0] = key;
                key_value[1] = " ";
            }
            if(Memory.command.contains(key_value[0])){
                throw new TypeErrorException("the name is define in native");
            }
            String[] values = new String[key_value.length-1];
            System.arraycopy(key_value,1,values,0,values.length);
            StringBuilder builder = new StringBuilder();
            for(int i = 0;i<values.length;i++){
                builder.append(values[i]);
                if(!(i == values.length-1)) {
                    builder.append("=");
                }
            }
            if(command.endsWith("=")){
                builder.append("=");
            }
            Var var;
            if(command.matches("(global::)*(const )[\\*]*+[a-z0-9A-Z_]+(=|:\\*)[\\s\\S]+")){
                var = new Var(key_value[0].split(" ")[1],builder.toString());
                var.readonly = true;
            }else{
                var = new Var(key_value[0],builder.toString());
            }
            return var;
        }
    }

    @Override
    public String toString() {
        return name+"="+value;
    }
}
