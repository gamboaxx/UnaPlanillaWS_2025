package cr.ac.una.unaplanilla.model;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DTO para TipoPlanilla compatible con JSON-B (evita serializar JavaFX Properties).
 */
public class TipoPlanillaDto {

    private StringProperty id;
    private StringProperty codigo;
    private StringProperty descripcion;
    private StringProperty planillaPorMes;

    private Integer anoUltimaPlanilla;
    private Integer mesUltimaPlanilla;
    private Integer numeroUltimaPlanilla;

    private BooleanProperty activo;
    private Long version;

    // Listas para UI y transporte
    private ObservableList<EmpleadoDto> empleados;
    private List<EmpleadoDto> empleadosEliminados;

    private Boolean modificado;

    public TipoPlanillaDto() {
        this.id = new SimpleStringProperty("");
        this.codigo = new SimpleStringProperty("");
        this.descripcion = new SimpleStringProperty("");
        this.planillaPorMes = new SimpleStringProperty("");
        this.activo = new SimpleBooleanProperty(true);
        this.modificado = false;
        this.empleados = FXCollections.observableArrayList();
        this.empleadosEliminados = new ArrayList<>();
    }

    // ====== Getters/Setters "planos" (serializables) ======

    public Long getId() {
        String s = this.id.get();
        return (s != null && !s.isEmpty()) ? Long.valueOf(s) : null;
    }

    public void setId(Long id) {
        this.id.set(id == null ? "" : id.toString());
    }

    public String getCodigo() {
        return codigo.get();
    }

    public void setCodigo(String codigo) {
        this.codigo.set(codigo);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public Integer getPlanillaPorMes() {
        String s = this.planillaPorMes.get();
        return (s != null && !s.isEmpty()) ? Integer.valueOf(s) : null;
    }

    public void setPlanillaPorMes(Integer planillaPorMes) {
        this.planillaPorMes.set(planillaPorMes == null ? "" : planillaPorMes.toString());
    }

    public Integer getAnoUltimaPlanilla() {
        return anoUltimaPlanilla;
    }

    public void setAnoUltimaPlanilla(Integer anoUltimaPlanilla) {
        this.anoUltimaPlanilla = anoUltimaPlanilla;
    }

    public Integer getMesUltimaPlanilla() {
        return mesUltimaPlanilla;
    }

    public void setMesUltimaPlanilla(Integer mesUltimaPlanilla) {
        this.mesUltimaPlanilla = mesUltimaPlanilla;
    }

    public Integer getNumeroUltimaPlanilla() {
        return numeroUltimaPlanilla;
    }

    public void setNumeroUltimaPlanilla(Integer numeroUltimaPlanilla) {
        this.numeroUltimaPlanilla = numeroUltimaPlanilla;
    }

    public Boolean getActivo() {
        return activo.get();
    }

    public void setActivo(Boolean activo) {
        this.activo.set(activo);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    // ====== JavaFX Properties (ocultas para JSON-B) ======

    @JsonbTransient
    public StringProperty getIdProperty() { return id; }

    @JsonbTransient
    public StringProperty getCodigoProperty() { return codigo; }

    @JsonbTransient
    public StringProperty getDescripcionProperty() { return descripcion; }

    @JsonbTransient
    public StringProperty getPlanillaPorMesProperty() { return planillaPorMes; }

    @JsonbTransient
    public BooleanProperty getActivoProperty() { return activo; }

    // ====== Empleados: ObservableList para UI + List para transporte ======

    /** Lista observable para la UI (NO serializar). */
    @JsonbTransient
    public ObservableList<EmpleadoDto> getEmpleados() {
        return empleados;
    }

    /** Setter de UI (NO usar para JSON-B). */
    @JsonbTransient
    public void setEmpleados(ObservableList<EmpleadoDto> empleados) {
        this.empleados = empleados;
    }

    /** Getter que JSON-B serializa como "empleados". */
    @JsonbProperty("empleados")
    public List<EmpleadoDto> getEmpleadosList() {
        return new ArrayList<>(empleados);
    }

    /** Setter que JSON-B usará al deserializar "empleados". */
    public void setEmpleadosList(List<EmpleadoDto> lista) {
        this.empleados.setAll(lista == null ? Collections.emptyList() : lista);
    }

    /** Esta lista ya es List, JSON-B la maneja bien tal cual. */
    public List<EmpleadoDto> getEmpleadosEliminados() {
        return empleadosEliminados;
    }

    public void setEmpleadosEliminados(List<EmpleadoDto> empleadosEliminados) {
        this.empleadosEliminados = (empleadosEliminados == null) ? new ArrayList<>() : empleadosEliminados;
    }

    // ====== equals/hashCode/toString ======

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final TipoPlanillaDto other = (TipoPlanillaDto) obj;
        return Objects.equals(this.id.get(), other.id.get());
    }

    @Override
    public String toString() {
        return "TipoPlanillaDto{" + "codigo=" + codigo + ", descripcion=" + descripcion + '}';
    }
}
