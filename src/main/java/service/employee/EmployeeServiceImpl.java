package service.employee;

import com.itextpdf.layout.element.Paragraph;
import model.Employee;
import repository.employee.EmployeeRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.io.FileOutputStream;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public boolean add(Employee employee) {
        return employeeRepository.add(employee);
    }

    @Override
    public boolean delete(Employee employee) {
        return employeeRepository.delete(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    @Override
    public void generateEmployeeSalesReport(String filePath) {
        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Sales Report"));
            document.add(new Paragraph(" "));

            List<Employee> employees = employeeRepository.findEmployees();
            for (Employee employee : employees) {
                int totalBooks = employeeRepository.getNumberOfBooksSoldPerEmployee(employee.getId());
                double totalSales = employeeRepository.getTotalSumOfBooks(employee.getId());

                document.add(new Paragraph("Employee: " + employee.getUsername()));
                document.add(new Paragraph("Books Sold: " + totalBooks));
                document.add(new Paragraph("Total Sales Value: " + totalSales + " RON"));
                document.add(new Paragraph(" "));
            }
            document.close();
            System.out.println("PDF generated successfully: " + filePath);
        } catch (Exception e) {
            System.err.println("Error generating PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
