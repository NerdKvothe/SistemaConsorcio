/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ufms.facom.apsoo.sysconsorcio.view;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import ufms.facom.apsoo.sysconsorcio.controller.VendaJpaController;
import ufms.facom.apsoo.sysconsorcio.controller.exceptions.NonexistentEntityException;
import ufms.facom.apsoo.sysconsorcio.model.Administradora;
import ufms.facom.apsoo.sysconsorcio.model.Cliente;
import ufms.facom.apsoo.sysconsorcio.model.Modeloveiculo;
import ufms.facom.apsoo.sysconsorcio.model.Venda;
import ufms.facom.apsoo.sysconsorcio.model.Vendedor;

/**
 *
 * @author joshua
 */
public class VendaView extends javax.swing.JPanel {
    
    private int acao;
    
    private EntityManagerFactory emf;
    private VendaJpaController vendaController;
    private Venda venda;
    
    private Administradora administradora;
    private Cliente cliente;
    private Vendedor vendedor;
    private Modeloveiculo modelo;

    /**
     * Creates new form ViewVendas
     */
    public VendaView() {
        initComponents();
        
        acao = 0; //iniciar
        
        emf = Persistence.createEntityManagerFactory("SistemaConsorcioPU");
        vendaController = new VendaJpaController(emf);
    }

    public int getAcao() {
        return acao;
    }
    
    public void iniciarView() {
        acao = 1; //iniciado
        
        administradora = null;
        cliente = null;
        vendedor = null;
        modelo = null;

        jButtonConsultar.setEnabled(true);
        jButtonInserir.setEnabled(true);
        jButtonAlterar.setEnabled(false);
        jButtonExcluir.setEnabled(false);
        jButtonConfirmar.setEnabled(false);
        jButtonCancelar.setEnabled(true);

        jButtonConsultarAdmin.setEnabled(false);
        jButtonConsultarCliente.setEnabled(false);
        jButtonConsultarModelo.setEnabled(false);
        jButtonConsultarVendedor.setEnabled(false);
        
        jTextFieldCodigoAdmin.setText("");
        jTextFieldCodigoCliente.setText("");
        jTextFieldCodigoModelo.setText("");
        jTextFieldCodigoVenda.setText("");
        jTextFieldCodigoVendedor.setText("");
        jTextFieldCota.setText("");
        jTextFieldDataCadastral.setText("");
        jTextFieldDataEntrada.setText("");
        jTextFieldDataInicioVigencia.setText("");
        jTextFieldGrupo.setText("");
        jTextFieldNomeAdmin.setText("");
        jTextFieldNomeCliente.setText("");
        jTextFieldNomeModelo.setText("");
        jTextFieldNomeVendedor.setText("");
        jTextFieldNumeroContrato.setText("");
        jTextFieldObservacao.setText("");
        jTextFieldQuantidadeParcela.setText("");
        jTextFieldValorBem.setText("");
        jTextFieldValorEntrada.setText("");

        setVisible(true);
    }
    
    public void sairView() {
        acao = 0; //iniciar
        setVisible(false);
    }

    private void model2View() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        administradora = venda.getCodigoAdm();
        cliente = venda.getCodigoCliente();
        vendedor = venda.getCodigoVendedor();
        modelo = venda.getCodigoModelo();
        
        jTextFieldCodigoAdmin.setText(administradora.getCodigoAdm().toString());
        jTextFieldCodigoCliente.setText(cliente.getCodigoCliente().toString());
        jTextFieldCodigoModelo.setText(modelo.getCodigoModelo().toString());
        jTextFieldCodigoVenda.setText(venda.getCodigoVenda().toString());
        jTextFieldCodigoVendedor.setText(vendedor.getCodigoVendedor().toString());
        jTextFieldCota.setText(venda.getCotaConsorcio().toString());
        jTextFieldDataCadastral.setValue(venda.getDataCadastro());
        jTextFieldDataEntrada.setValue(venda.getDtParcEntrada());
        jTextFieldDataInicioVigencia.setValue(venda.getDtIniVigencia());
        jTextFieldGrupo.setText(venda.getGrupoConsorcio().toString());
        jTextFieldNomeAdmin.setText(administradora.getNome());
        jTextFieldNomeCliente.setText(cliente.getNome());
        jTextFieldNomeModelo.setText(modelo.getDescricao());
        jTextFieldNomeVendedor.setText(vendedor.getNome());
        jTextFieldNumeroContrato.setText(venda.getNroContrato());
        jTextFieldObservacao.setText(venda.getObservacao());
        jTextFieldQuantidadeParcela.setText(venda.getQtdParcelas().toString());
        jTextFieldValorBem.setValue(venda.getVlrBem());
        jTextFieldValorEntrada.setValue(venda.getVlrParcEntrada());
    }

    private void view2Model() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        venda.setCodigoAdm(administradora);
        venda.setCodigoCliente(cliente);
        venda.setCodigoModelo(modelo);
//        venda.setCodigoVenda(Integer.parseInt(jTextFieldCodigoVenda.getText()));
        venda.setCodigoVendedor(vendedor);
        venda.setCotaConsorcio(Integer.parseInt(jTextFieldCota.getText()));
        try {
            venda.setDataCadastro(dateFormat.parse(jTextFieldDataCadastral.getText()));
        } catch (ParseException ex) {
            Logger.getLogger(VendaView.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            venda.setDtParcEntrada(dateFormat.parse(jTextFieldDataEntrada.getText()));
        } catch (ParseException ex) {
            Logger.getLogger(VendaView.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            venda.setDtIniVigencia(dateFormat.parse(jTextFieldDataInicioVigencia.getText()));
        } catch (ParseException ex) {
            Logger.getLogger(VendaView.class.getName()).log(Level.SEVERE, null, ex);
        }
        venda.setGrupoConsorcio(Integer.parseInt(jTextFieldGrupo.getText()));
        venda.setNroContrato(jTextFieldNumeroContrato.getText());
        venda.setObservacao(jTextFieldObservacao.getText());
        venda.setQtdParcelas(Integer.parseInt(jTextFieldQuantidadeParcela.getText()));
        venda.setVlrBem(Double.parseDouble(jTextFieldValorBem.getText().replace(',', '.')));
        venda.setVlrParcEntrada(Double.parseDouble(jTextFieldValorEntrada.getText().replace(',', '.')));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonConsultar = new javax.swing.JButton();
        jButtonInserir = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonConfirmar = new javax.swing.JButton();
        jTextFieldNomeCliente = new javax.swing.JTextField();
        jTextFieldNomeAdmin = new javax.swing.JTextField();
        jLabelVenda = new javax.swing.JLabel();
        jLabelCliente = new javax.swing.JLabel();
        jLabelAdmin = new javax.swing.JLabel();
        jTextFieldNomeVendedor = new javax.swing.JTextField();
        jLabelVendedor = new javax.swing.JLabel();
        jLabelDataCadastral = new javax.swing.JLabel();
        jLabelNumeroContrato = new javax.swing.JLabel();
        jTextFieldObservacao = new javax.swing.JTextField();
        jTextFieldNomeModelo = new javax.swing.JTextField();
        jLabelDataEntrada = new javax.swing.JLabel();
        jLabelValorEntrada = new javax.swing.JLabel();
        jLabelValorBem = new javax.swing.JLabel();
        jLabelModelo = new javax.swing.JLabel();
        jLabelObservacao = new javax.swing.JLabel();
        jLabelDataInicioVigencia = new javax.swing.JLabel();
        jLabelQuatidadeParcela = new javax.swing.JLabel();
        jLabelGrupo = new javax.swing.JLabel();
        jLabelCota = new javax.swing.JLabel();
        jButtonCancelar = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jTextFieldCodigoVenda = new javax.swing.JFormattedTextField();
        jTextFieldCodigoCliente = new javax.swing.JFormattedTextField();
        jTextFieldDataCadastral = new javax.swing.JFormattedTextField();
        jTextFieldCodigoAdmin = new javax.swing.JFormattedTextField();
        jTextFieldCodigoVendedor = new javax.swing.JFormattedTextField();
        jButtonConsultarCliente = new javax.swing.JButton();
        jButtonConsultarAdmin = new javax.swing.JButton();
        jButtonConsultarVendedor = new javax.swing.JButton();
        jButtonConsultarModelo = new javax.swing.JButton();
        jTextFieldCodigoModelo = new javax.swing.JFormattedTextField();
        jTextFieldDataInicioVigencia = new javax.swing.JFormattedTextField();
        jTextFieldQuantidadeParcela = new javax.swing.JFormattedTextField();
        jTextFieldGrupo = new javax.swing.JFormattedTextField();
        jTextFieldCota = new javax.swing.JFormattedTextField();
        jTextFieldDataEntrada = new javax.swing.JFormattedTextField();
        jTextFieldValorEntrada = new javax.swing.JFormattedTextField();
        jTextFieldValorBem = new javax.swing.JFormattedTextField();
        jTextFieldNumeroContrato = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(800, 400));

        jButtonConsultar.setLabel("Consultar");
        jButtonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultarActionPerformed(evt);
            }
        });

        jButtonInserir.setText("Inserir");
        jButtonInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInserirActionPerformed(evt);
            }
        });

        jButtonAlterar.setText("Alterar");
        jButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarActionPerformed(evt);
            }
        });

        jButtonExcluir.setText("Excluir");
        jButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirActionPerformed(evt);
            }
        });

        jButtonConfirmar.setText("Confirmar");
        jButtonConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarActionPerformed(evt);
            }
        });

        jTextFieldNomeCliente.setColumns(40);

        jTextFieldNomeAdmin.setColumns(40);

        jLabelVenda.setText("Código");

        jLabelCliente.setText("Cliente");

        jLabelAdmin.setText("Administradora");

        jTextFieldNomeVendedor.setColumns(40);

        jLabelVendedor.setText("Vendedor");

        jLabelDataCadastral.setText("Data Cadastral");

        jLabelNumeroContrato.setText("Nº. Contrato");

        jTextFieldObservacao.setColumns(30);

        jTextFieldNomeModelo.setColumns(15);

        jLabelDataEntrada.setText("Data Entrada");

        jLabelValorEntrada.setText("Valor Entrada");

        jLabelValorBem.setText("Valor do Bem");

        jLabelModelo.setText("Modelo");

        jLabelObservacao.setText("Obs.");

        jLabelDataInicioVigencia.setText("Dt. Início Vigência");

        jLabelQuatidadeParcela.setText("Qtde. Parcelas");

        jLabelGrupo.setText("Grupo");

        jLabelCota.setText("Cota");

        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jButtonSair.setText("Sair");
        jButtonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSairActionPerformed(evt);
            }
        });

        jTextFieldCodigoVenda.setColumns(10);
        jTextFieldCodigoVenda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jTextFieldCodigoVenda.setEnabled(false);

        jTextFieldCodigoCliente.setColumns(10);
        jTextFieldCodigoCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        jTextFieldDataCadastral.setColumns(10);
        jTextFieldDataCadastral.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));
        jTextFieldDataCadastral.setEnabled(false);

        jTextFieldCodigoAdmin.setColumns(10);
        jTextFieldCodigoAdmin.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        jTextFieldCodigoVendedor.setColumns(10);
        jTextFieldCodigoVendedor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        jButtonConsultarCliente.setText("...");
        jButtonConsultarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultarClienteActionPerformed(evt);
            }
        });

        jButtonConsultarAdmin.setText("...");
        jButtonConsultarAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultarAdminActionPerformed(evt);
            }
        });

        jButtonConsultarVendedor.setText("...");
        jButtonConsultarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultarVendedorActionPerformed(evt);
            }
        });

        jButtonConsultarModelo.setText("...");
        jButtonConsultarModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultarModeloActionPerformed(evt);
            }
        });

        jTextFieldCodigoModelo.setColumns(5);

        jTextFieldDataInicioVigencia.setColumns(10);
        jTextFieldDataInicioVigencia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));

        jTextFieldQuantidadeParcela.setColumns(10);
        jTextFieldQuantidadeParcela.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        jTextFieldGrupo.setColumns(10);
        jTextFieldGrupo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        jTextFieldCota.setColumns(10);
        jTextFieldCota.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        jTextFieldDataEntrada.setColumns(10);
        jTextFieldDataEntrada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));

        jTextFieldValorEntrada.setColumns(15);
        jTextFieldValorEntrada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        jTextFieldValorBem.setColumns(15);
        jTextFieldValorBem.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        jTextFieldNumeroContrato.setColumns(15);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonConsultar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonInserir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSair))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelNumeroContrato)
                            .addComponent(jLabelVenda)
                            .addComponent(jLabelCliente)
                            .addComponent(jLabelAdmin)
                            .addComponent(jLabelVendedor)
                            .addComponent(jLabelDataInicioVigencia)
                            .addComponent(jLabelQuatidadeParcela)
                            .addComponent(jLabelGrupo)
                            .addComponent(jLabelCota))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldCodigoAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldCodigoVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextFieldNomeVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButtonConsultarVendedor))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextFieldNomeAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButtonConsultarAdmin))))
                            .addComponent(jTextFieldDataInicioVigencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jTextFieldNumeroContrato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(128, 128, 128))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jTextFieldQuantidadeParcela, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextFieldGrupo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextFieldCota, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelValorBem, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabelValorEntrada, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabelDataEntrada, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabelModelo, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabelObservacao, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextFieldCodigoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabelDataCadastral)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextFieldDataCadastral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jTextFieldCodigoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextFieldNomeModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButtonConsultarModelo))
                                        .addComponent(jTextFieldObservacao))
                                    .addComponent(jTextFieldValorEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldValorBem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextFieldCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonConsultarCliente)))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonConsultar)
                    .addComponent(jButtonInserir)
                    .addComponent(jButtonAlterar)
                    .addComponent(jButtonExcluir)
                    .addComponent(jButtonConfirmar)
                    .addComponent(jButtonCancelar)
                    .addComponent(jButtonSair))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVenda)
                    .addComponent(jLabelDataCadastral)
                    .addComponent(jTextFieldCodigoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDataCadastral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCliente)
                    .addComponent(jTextFieldCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonConsultarCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNomeAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAdmin)
                    .addComponent(jTextFieldCodigoAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonConsultarAdmin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNomeVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelVendedor)
                    .addComponent(jTextFieldCodigoVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonConsultarVendedor))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelDataEntrada)
                            .addComponent(jTextFieldDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelValorEntrada)
                            .addComponent(jTextFieldValorEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelValorBem)
                            .addComponent(jTextFieldValorBem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNomeModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelModelo)
                            .addComponent(jButtonConsultarModelo)
                            .addComponent(jTextFieldCodigoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelObservacao)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelNumeroContrato)
                            .addComponent(jTextFieldNumeroContrato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelDataInicioVigencia)
                            .addComponent(jTextFieldDataInicioVigencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelQuatidadeParcela)
                            .addComponent(jTextFieldQuantidadeParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelGrupo)
                            .addComponent(jTextFieldGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCota)
                            .addComponent(jTextFieldCota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jButtonConsultar.getAccessibleContext().setAccessibleName("jButtonConsultar");
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarActionPerformed
        // TODO add your handling code here:
        acao = 2; //consultar
                
        jButtonConsultar.setEnabled(true);
        jButtonInserir.setEnabled(true);
        jButtonAlterar.setEnabled(true);
        jButtonExcluir.setEnabled(true);
        jButtonConfirmar.setEnabled(false);
        jButtonCancelar.setEnabled(false);
        
        jButtonConsultarAdmin.setEnabled(false);
        jButtonConsultarCliente.setEnabled(false);
        jButtonConsultarModelo.setEnabled(false);
        jButtonConsultarVendedor.setEnabled(false);

        ConsultaVendaView cvv = new ConsultaVendaView(null, true);
        cvv.setVisible(true);

        if (cvv.getSelected() != -1) {
            venda = cvv.getVenda();
            model2View();
        }

        jTextFieldCodigoAdmin.setEditable(false);
        jTextFieldCodigoCliente.setEditable(false);
        jTextFieldCodigoModelo.setEditable(false);
        jTextFieldCodigoVenda.setEditable(false);
        jTextFieldCodigoVendedor.setEditable(false);
        jTextFieldCota.setEditable(false);
        jTextFieldDataCadastral.setEditable(false);
        jTextFieldDataEntrada.setEditable(false);
        jTextFieldDataInicioVigencia.setEditable(false);
        jTextFieldGrupo.setEditable(false);
        jTextFieldNomeAdmin.setEditable(false);
        jTextFieldNomeCliente.setEditable(false);
        jTextFieldNomeModelo.setEditable(false);
        jTextFieldNomeVendedor.setEditable(false);
        jTextFieldNumeroContrato.setEditable(false);
        jTextFieldObservacao.setEditable(false);
        jTextFieldQuantidadeParcela.setEditable(false);
        jTextFieldValorBem.setEditable(false);
        jTextFieldValorEntrada.setEditable(false);

    }//GEN-LAST:event_jButtonConsultarActionPerformed

    private void jButtonInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInserirActionPerformed
        // TODO add your handling code here:
        acao = 3; //inserir
        
        jButtonConsultar.setEnabled(false);
        jButtonInserir.setEnabled(false);
        jButtonAlterar.setEnabled(false);
        jButtonExcluir.setEnabled(false);
        jButtonConfirmar.setEnabled(true);
        jButtonCancelar.setEnabled(true);
        
        jButtonConsultarAdmin.setEnabled(true);
        jButtonConsultarCliente.setEnabled(true);
        jButtonConsultarModelo.setEnabled(true);
        jButtonConsultarVendedor.setEnabled(true);
        
        jTextFieldCodigoAdmin.setText("");
        jTextFieldCodigoCliente.setText("");
        jTextFieldCodigoModelo.setText("");
        jTextFieldCodigoVenda.setText("");
        jTextFieldCodigoVendedor.setText("");
        jTextFieldCota.setText("");

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        jTextFieldDataCadastral.setValue(date);

        jTextFieldDataEntrada.setText("");
        jTextFieldDataInicioVigencia.setText("");
        jTextFieldGrupo.setText("");
        jTextFieldNomeAdmin.setText("");
        jTextFieldNomeCliente.setText("");
        jTextFieldNomeModelo.setText("");
        jTextFieldNomeVendedor.setText("");
        jTextFieldNumeroContrato.setText("");
        jTextFieldObservacao.setText("");
        jTextFieldQuantidadeParcela.setText("");
        jTextFieldValorBem.setText("");
        jTextFieldValorEntrada.setText("");

        jTextFieldCodigoAdmin.setEditable(false);
        jTextFieldCodigoCliente.setEditable(false);
        jTextFieldCodigoModelo.setEditable(false);
        jTextFieldCodigoVenda.setEditable(false);
        jTextFieldCodigoVendedor.setEditable(false);
        jTextFieldCota.setEditable(true);
        jTextFieldDataCadastral.setEditable(true);
        jTextFieldDataEntrada.setEditable(true);
        jTextFieldDataInicioVigencia.setEditable(true);
        jTextFieldGrupo.setEditable(true);
        jTextFieldNomeAdmin.setEditable(false);
        jTextFieldNomeCliente.setEditable(false);
        jTextFieldNomeModelo.setEditable(false);
        jTextFieldNomeVendedor.setEditable(false);
        jTextFieldNumeroContrato.setEditable(true);
        jTextFieldObservacao.setEditable(true);
        jTextFieldQuantidadeParcela.setEditable(true);
        jTextFieldValorBem.setEditable(true);
        jTextFieldValorEntrada.setEditable(true);

    }//GEN-LAST:event_jButtonInserirActionPerformed

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        // TODO add your handling code here:
        acao = 4; //alterar

        jButtonConsultar.setEnabled(false);
        jButtonInserir.setEnabled(false);
        jButtonAlterar.setEnabled(false);
        jButtonExcluir.setEnabled(false);
        jButtonConfirmar.setEnabled(true);
        jButtonCancelar.setEnabled(true);
        
        jButtonConsultarAdmin.setEnabled(true);
        jButtonConsultarCliente.setEnabled(true);
        jButtonConsultarModelo.setEnabled(true);
        jButtonConsultarVendedor.setEnabled(true);

        jTextFieldCodigoAdmin.setEditable(false);
        jTextFieldCodigoCliente.setEditable(false);
        jTextFieldCodigoModelo.setEditable(false);
        jTextFieldCodigoVenda.setEditable(false);
        jTextFieldCodigoVendedor.setEditable(false);
        jTextFieldCota.setEditable(true);
        jTextFieldDataCadastral.setEditable(true);
        jTextFieldDataEntrada.setEditable(true);
        jTextFieldDataInicioVigencia.setEditable(true);
        jTextFieldGrupo.setEditable(true);
        jTextFieldNomeAdmin.setEditable(false);
        jTextFieldNomeCliente.setEditable(false);
        jTextFieldNomeModelo.setEditable(false);
        jTextFieldNomeVendedor.setEditable(false);
        jTextFieldNumeroContrato.setEditable(true);
        jTextFieldObservacao.setEditable(true);
        jTextFieldQuantidadeParcela.setEditable(true);
        jTextFieldValorBem.setEditable(true);
        jTextFieldValorEntrada.setEditable(true);
        
    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        // TODO add your handling code here:
        acao = 5; //excluir

        if (JOptionPane.showConfirmDialog(null, "Confirma a exclusão da Venda???", "Excluir", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            view2Model();

            try {
                vendaController.destroy(venda.getCodigoVenda());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(VendaView.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            iniciarView();
        }
        
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarActionPerformed
        // TODO add your handling code here:
        switch (acao) {
            case 3:
                // inserir dados
                venda = new Venda();
                view2Model();
                
                try {
                    vendaController.create(venda);
                } catch (Exception ex) {
                    Logger.getLogger(VendaView.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                iniciarView();
                break;

            case 4:
                // alterar dados
                view2Model();

                try {
                    vendaController.edit(venda);
                } catch (Exception ex) {
                    Logger.getLogger(VendaView.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                iniciarView();
                break;
        }
        
    }//GEN-LAST:event_jButtonConfirmarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        // TODO add your handling code here:
        iniciarView();
        
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed
        // TODO add your handling code here:
        sairView();
        
    }//GEN-LAST:event_jButtonSairActionPerformed

    private void jButtonConsultarAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarAdminActionPerformed
        // TODO add your handling code here:
        ConsultaAdminView cav = new ConsultaAdminView(null, true);
        cav.setVisible(true);
        
        if (cav.getSelected() != -1) {
            administradora = cav.getAdministradora();
            jTextFieldCodigoAdmin.setValue(administradora.getCodigoAdm());
            jTextFieldNomeAdmin.setText(administradora.getNome());
        }
    }//GEN-LAST:event_jButtonConsultarAdminActionPerformed

    private void jButtonConsultarVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarVendedorActionPerformed
        // TODO add your handling code here:
        ConsultaVendedorView cvv = new ConsultaVendedorView(null, true);
        cvv.setVisible(true);
        
        if (cvv.getSelected() != -1) {
            vendedor = cvv.getVendedor();
            jTextFieldCodigoVendedor.setValue(vendedor.getCodigoVendedor());
            jTextFieldNomeVendedor.setText(vendedor.getNome());
        }

    }//GEN-LAST:event_jButtonConsultarVendedorActionPerformed

    private void jButtonConsultarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarClienteActionPerformed
        // TODO add your handling code here:
        ConsultaClienteView ccv = new ConsultaClienteView(null, true);
        ccv.setVisible(true);
        
        if (ccv.getSelected() != -1) {
            cliente = ccv.getCliente();
            jTextFieldCodigoCliente.setValue(cliente.getCodigoCliente());
            jTextFieldNomeCliente.setText(cliente.getNome());
        }
        
    }//GEN-LAST:event_jButtonConsultarClienteActionPerformed

    private void jButtonConsultarModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarModeloActionPerformed
        // TODO add your handling code here:
        ConsultaModeloView cmv = new ConsultaModeloView(null, true);
        cmv.setVisible(true);
        
        if (cmv.getSelected() != -1) {
            modelo = cmv.getModelo();
            jTextFieldCodigoModelo.setValue(modelo.getCodigoModelo());
            jTextFieldNomeModelo.setText(modelo.getDescricao());
        }
    }//GEN-LAST:event_jButtonConsultarModeloActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonConfirmar;
    private javax.swing.JButton jButtonConsultar;
    private javax.swing.JButton jButtonConsultarAdmin;
    private javax.swing.JButton jButtonConsultarCliente;
    private javax.swing.JButton jButtonConsultarModelo;
    private javax.swing.JButton jButtonConsultarVendedor;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonInserir;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JLabel jLabelAdmin;
    private javax.swing.JLabel jLabelCliente;
    private javax.swing.JLabel jLabelCota;
    private javax.swing.JLabel jLabelDataCadastral;
    private javax.swing.JLabel jLabelDataEntrada;
    private javax.swing.JLabel jLabelDataInicioVigencia;
    private javax.swing.JLabel jLabelGrupo;
    private javax.swing.JLabel jLabelModelo;
    private javax.swing.JLabel jLabelNumeroContrato;
    private javax.swing.JLabel jLabelObservacao;
    private javax.swing.JLabel jLabelQuatidadeParcela;
    private javax.swing.JLabel jLabelValorBem;
    private javax.swing.JLabel jLabelValorEntrada;
    private javax.swing.JLabel jLabelVenda;
    private javax.swing.JLabel jLabelVendedor;
    private javax.swing.JFormattedTextField jTextFieldCodigoAdmin;
    private javax.swing.JFormattedTextField jTextFieldCodigoCliente;
    private javax.swing.JFormattedTextField jTextFieldCodigoModelo;
    private javax.swing.JFormattedTextField jTextFieldCodigoVenda;
    private javax.swing.JFormattedTextField jTextFieldCodigoVendedor;
    private javax.swing.JFormattedTextField jTextFieldCota;
    private javax.swing.JFormattedTextField jTextFieldDataCadastral;
    private javax.swing.JFormattedTextField jTextFieldDataEntrada;
    private javax.swing.JFormattedTextField jTextFieldDataInicioVigencia;
    private javax.swing.JFormattedTextField jTextFieldGrupo;
    private javax.swing.JTextField jTextFieldNomeAdmin;
    private javax.swing.JTextField jTextFieldNomeCliente;
    private javax.swing.JTextField jTextFieldNomeModelo;
    private javax.swing.JTextField jTextFieldNomeVendedor;
    private javax.swing.JTextField jTextFieldNumeroContrato;
    private javax.swing.JTextField jTextFieldObservacao;
    private javax.swing.JFormattedTextField jTextFieldQuantidadeParcela;
    private javax.swing.JFormattedTextField jTextFieldValorBem;
    private javax.swing.JFormattedTextField jTextFieldValorEntrada;
    // End of variables declaration//GEN-END:variables
}
