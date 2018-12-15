package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

public class DataModelSettings {

        String name;
        String type;
        String feature;

        public DataModelSettings(String name, String type, String feature ) {
            this.name=name;
            this.type=type;
            this.feature=feature;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getFeature() {
            return feature;
        }
}