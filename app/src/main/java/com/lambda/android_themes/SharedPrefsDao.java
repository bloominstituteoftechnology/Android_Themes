package com.lambda.android_themes;



import java.util.ArrayList;

public class SharedPrefsDao {
    private static ArrayList<Book> alBook;


    public SharedPrefsDao(){
        int iInitialCapacity=100;
        this.alBook=new ArrayList<Book>(iInitialCapacity);

    }
    public SharedPrefsDao(String strIds){
        int iInitialCapacity=100;
        this.alBook=new ArrayList<Book>(iInitialCapacity);
        String[] strID=strIds.split( "," );
        for(int i=0;i<strID.length;i++){
            Book bk=new Book(strID[i],"title","reason",false);
            this.alBook.add(bk);
        }



    }

    public ArrayList<Book> AllBook(){
        return alBook;
    }
    public String[] getAllBookIds(){
        String[] straryAllTheID=new String[this.alBook.size()];
        for(int i=0;i<this.alBook.size();i++){
            straryAllTheID[i]=alBook.get(i).getStrID();

        }
        return straryAllTheID;
    }
    public String getNextId(String strCurrentID){

        String[] straryAllTheID=new String[this.alBook.size()];
        for(int i=0;i<straryAllTheID.length;i++){
            if (alBook.get(i).getStrID().equals( strCurrentID )){
                if(straryAllTheID.length-1==i){
                    return  "";
                }else{
                    return alBook.get(i+1).getStrID();
                }

            }
        }
        return "Invalid ID";

    }
    public String getInitialID(){
        if (alBook.size()==0){

            return "new";
        }

        return alBook.get(0).strID;
    }

    public Book bkBookByID(String strCurrentID){

        for(int i=0;i<this.alBook.size();i++){
            if(alBook.get(i).getStrID().equals( strCurrentID )){
                return alBook.get( i );
            }
        }
        return null;
    }

    public Book bkBookByID(int iID){
                return alBook.get( iID );
    }

    public String strBookCSVByID(String strCurrentID){
        for(int i=0;i<this.alBook.size();i++){
            if(alBook.get(i).getStrID().equals( strCurrentID )){
                return alBook.get( i ).toCsvString();
            }
        }
        return "Invalid ID";
    }

    public SharedPrefsDao updateBook(Book bookToBeUpdated){
        String strID=bookToBeUpdated.strID;
        if (strID.equals( "new" )){
            if(bookToBeUpdated.getStrTitle().equals( "" ))return this;
            String newID="1";
            for(int i=0;i<this.alBook.size();i++){
                if(alBook.get(i).getStrID().equals(newID)){

                    newID=Integer.toString( i+2 );
                }else{
                    break;
                }
            }
            bookToBeUpdated.setStrID( newID );
            alBook.add( bookToBeUpdated );
        }else{
            if(this.alBook.size()==0){
                if(bookToBeUpdated.getStrTitle().equals( "" )){
                    if (size()!=0)alBook.remove( 0);
                }else{
                    alBook.add( bookToBeUpdated );
                }
            }else{
                for(int i=0;i<size();i++) {
                    if (alBook.get( i ).getStrID().equals( strID )) {
                        if (bookToBeUpdated.getStrTitle().equals( "" )) {  //delete
                            alBook.remove( i );

                        } else {
                            alBook.get( i ).update( bookToBeUpdated );
                        }
                    }else{
                        //   alBook.get( i ).update( bookToBeUpdated );
                    }
                }
            }
        }
        return this;
    }
    public int size(){
        return this.alBook.size();
    }
}
