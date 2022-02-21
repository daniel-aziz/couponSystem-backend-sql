package DAO;

import Beans.Company;

import java.sql.SQLException;
import java.util.List;



public interface CompaniesDAO {
    boolean isCompanyExits (String email, String password) throws SQLException;
    void addCompany (Company company) throws SQLException;
    void updateCompany (Company company) throws SQLException;
    void deleteCompany (int companyId) throws SQLException;
    List<Company> getAllCompanies() throws SQLException;
    Company getOneCompany(int companyId) throws SQLException;
    int getCompanyID(String email) throws SQLException;

}
