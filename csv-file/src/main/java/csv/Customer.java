package csv;

import java.util.Date;

public class Customer {
    public String ID;
    public String Name;
    public String Description;
    public String Company;
    public CustomerColor Color;
    public Date CustomerDate;

    public Customer(String id, String name, String description, String company, CustomerColor color, Date customerDate) {
        ID = id;
        Name = name;
        Description = description;
        Company = company;
        Color = color;
        CustomerDate = customerDate;
    }

    public String[] toArray() {
        String[] retVal = new String[6];
        retVal[0] = ID;
        retVal[1] = Name;
        retVal[2] = Color.equals(CustomerColor.Red) ? "" : Description;
        retVal[3] = Company;
        retVal[4] = Color.toString();
        retVal[5] = CustomerDate.toString();
        return retVal;
    }
}
