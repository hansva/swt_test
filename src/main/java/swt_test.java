import org.apache.hop.core.logging.HopLogStore;
import org.apache.hop.ui.core.PropsUi;
import org.apache.hop.ui.core.widget.ColumnInfo;
import org.apache.hop.ui.core.widget.TableView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;


public class swt_test {
    private ColumnInfo[] colinfTableView;
    private ColumnInfo[] colinfSecondTableView;
    private TableView wTableView;
    private StyledText wTableViewMessage;
    private TableView wSecondTableView;

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
        HopLogStore.init();
        try {
            swt_test window = new swt_test();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = new Display ();
        final int dialogStyle = SWT.CLOSE | SWT.MODELESS | SWT.MAX | SWT.MIN  ;
        Shell shell = new Shell (display, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX);
        shell.setText ("Shell");
        PropsUi.setLook(shell);
        FormLayout formLayout = new FormLayout();
        formLayout.marginWidth = PropsUi.getFormMargin();
        formLayout.marginHeight = PropsUi.getFormMargin();
        shell.setLayout(formLayout);

        ModifyListener lsMod = new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent modifyEvent) {
                checkTableContent();
            }
        };

        colinfTableView =
                new ColumnInfo[] {
                        new ColumnInfo(
                                "First column",
                                ColumnInfo.COLUMN_TYPE_TEXT,
                                false),
                        new ColumnInfo(
                                "Second column",
                                ColumnInfo.COLUMN_TYPE_TEXT,
                                false)
                };

        wTableView =
                new TableView(
                        null,
                        shell,
                        SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI,
                        colinfTableView,
                        1,
                        lsMod,
                        PropsUi.getInstance());

        TableView.ITableViewModifyListener modifyListener = new TableView.ITableViewModifyListener() {

            @Override
            public void moveRow(int i, int i1) {
                checkTableContent();
            }

            @Override
            public void insertRow(int i) {
                checkTableContent();
            }

            @Override
            public void cellFocusLost(int i) {
                checkTableContent();
            }

            @Override
            public void delete(int[] ints) {
                checkTableContent();
            }
        };
        wTableView.setTableViewModifyListener(modifyListener);

        FormData fdTableView = new FormData();
        fdTableView.left = new FormAttachment(0, 0);
        fdTableView.right = new FormAttachment(100, 0);
        wTableView.setLayoutData(fdTableView);

        wTableViewMessage = new StyledText(shell, SWT.NONE );
        wTableViewMessage.setText("test");
        wTableViewMessage.setEditable(false);
        wTableViewMessage.setVisible(false);
        wTableViewMessage.setBackground(new Color(shell.getDisplay(), 255, 0, 0));

        FormData fdTableViewMessage = new FormData();
        fdTableViewMessage.left = new FormAttachment(0, 0);
        fdTableViewMessage.right = new FormAttachment(100, 0);
        fdTableViewMessage.top = new FormAttachment(wTableView, 0);
        wTableViewMessage.setLayoutData(fdTableViewMessage);

        colinfSecondTableView =
                new ColumnInfo[] {
                        new ColumnInfo(
                                "First column",
                                ColumnInfo.COLUMN_TYPE_TEXT,
                                false),
                        new ColumnInfo(
                                "Second column",
                                ColumnInfo.COLUMN_TYPE_TEXT,
                                false)
                };

        wSecondTableView =
                new TableView(
                        null,
                        shell,
                        SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI,
                        colinfSecondTableView,
                        1,
                        lsMod,
                        PropsUi.getInstance());

        FormData fdSecondTableViewMessage = new FormData();
        fdSecondTableViewMessage.left = new FormAttachment(0, 0);
        fdSecondTableViewMessage.right = new FormAttachment(100, 0);
        fdSecondTableViewMessage.top = new FormAttachment(wTableViewMessage, 0);
        wSecondTableView.setLayoutData(fdSecondTableViewMessage);





        shell.pack();
        shell.layout();
        shell.open ();

        while (!shell.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void checkTableContent(){
        //Table changed check content
        String[] firstCol = wTableView.getItems(0);
        String[] secondCol = wTableView.getItems(1);
        // Reset the error field
        wTableViewMessage.setVisible(false);
        //check if first column contains numbers
        for(int i=0; i<firstCol.length; i++){
            if(!isNumeric(firstCol[i]) && !firstCol[i].isEmpty()){
                wTableViewMessage.setText("Non numeric value found in row: " + i);
                wTableViewMessage.setVisible(true);
                break;
            }
        }
    }
}
