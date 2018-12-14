package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.SQLiteDB;

public class Property {
    private int property_id;
    private String property_name;
    private String property_value;

    public Property(int property_id, String property_name, String property_value) {
        this.property_id = property_id;
        this.property_name = property_name;
        this.property_value = property_value;
    }
    public Property(){

    }

    public void setProperty_id(int property_id) {
        this.property_id = property_id;
    }

    public void setProperty_name(String property_name) {
        this.property_name = property_name;
    }

    public void setProperty_value(String property_value) {
        this.property_value = property_value;
    }

    public int getProperty_id() {
        return property_id;
    }

    public String getProperty_name() {
        return property_name;
    }

    public String getProperty_value() {
        return property_value;
    }


}
