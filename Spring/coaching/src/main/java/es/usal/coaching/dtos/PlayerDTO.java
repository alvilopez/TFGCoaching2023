package es.usal.coaching.dtos;



public class PlayerDTO {
    
    private Long id;
    private String name;
    private String surname;
    private String number;
    private String position;
    private String email;
    private Integer age;
    private Float weight;
    private Float hight;
    private String dni;
    private String imgName;

    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public String getImgName() {
        return imgName;
    }



    public void setImgName(String imgName) {
        this.imgName = imgName;
    }



    public PlayerDTO( String name, String surname, String number, String position, String email, Integer age,
            Float weight, Float hight, String dni) {
        
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.position = position;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.hight = hight;
        this.dni = dni;
    }

    

    public PlayerDTO() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Float getWeight() {
        return weight;
    }
    public void setWeight(Float weight) {
        this.weight = weight;
    }
    public Float getHight() {
        return hight;
    }
    public void setHight(Float hight) {
        this.hight = hight;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    
}
