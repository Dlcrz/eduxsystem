//funcion de notifiaciones toastr
function mostrarNotificacion(tipo, titulo, descripcion) {
    toastr[tipo](descripcion, titulo);
}
// Configuración global de Toastr
toastr.options = {
    "closeButton": true,
    "debug": false,
    "newestOnTop": false,
    "progressBar": true,
    "positionClass": "toast-top-right",
    "preventDuplicates": true,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "4000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
};
function configurarEliminacionDinamica(idTabla, claseBoton, mensajeConfirmacion) {
    $(`#${idTabla}`).on('click', `.${claseBoton}`, function (e) {
        e.preventDefault();

        var url = $(this).attr('href');

        Swal.fire({
            title: '¿Estás seguro?',
            text: mensajeConfirmacion,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = url;
            }
        });
    });
}
//funcion data tables, id de tabla, botones = si or no
function configurarDataTable(idTabla, botones) {

    // Verifica si el DataTable ya está inicializado y lo destruye si es necesario
    if ($.fn.DataTable.isDataTable(`#${idTabla}`)) {
        $(`#${idTabla}`).DataTable().destroy();
        // Remueve el footer duplicado para evitar su re-creación
        $(`#${idTabla} tfoot`).remove();
    }

    // Asegura que se agregue un pie de página solo una vez
    if ($(`#${idTabla} tfoot`).length === 0) {
        // Aquí agregas el pie de página si es necesario, asegurándote de que solo se haga una vez
        //$(`#${idTabla}`).append('<tfoot><tr><th>Footer Content Here</th></tr></tfoot>');
    }
    var config = {
        language: {
            "sProcessing": "Procesando...",
            "sLengthMenu": "Mostrar _MENU_ registros",
            "sZeroRecords": "No se encontraron resultados",
            "sEmptyTable": '<img src="/draw-svg/undraw_server_re_twwj.svg" class="img-responsive" style="width: 250px; height: 250px;"><br> No hay datos disponibles en el módulo.',
            "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
            "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
            "sSearch": "Buscar:",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst": "Primero",
                "sLast": "Último",
                "sNext": "Siguiente",
                "sPrevious": "Anterior"
            },
            "oAria": {
                "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
            }
        },
        dom: '<"dt-container"<"length"l><"filter"f>>>tip',
        scrollX: true,
        pageLength: 15,
        lengthMenu: [15, 20, 30, 50, 100],
    };

    // Evaluamos si se deben agregar los botones de exportación
    if (botones === "si") {
        config.dom = '<"dt-container"<"length"l><"buttons"B><"filter"f>>>tip';
        config.buttons = [{
            extend: 'excelHtml5',
            text: '<i class="fa fa-file-excel-o"></i>&nbsp;Exportar excel',
            title: 'Tabla ' + new Date().toLocaleDateString(),
            titleAttr: 'excel',
            className: 'btn btn-success export excel',
            exportOptions: {
                columns: ':visible:not(.no-print)'
            }
        }];
    }
    var table = $(`#${idTabla}`).DataTable(config);
    return table; // Retorna la instancia del DataTable por si es necesario manipularla
}

function initializeSelect2() {
    $('.select-class-2s').each(function() {
        $(this).select2({
            dropdownParent: $(this).closest('.modal'),  // Asigna el modal más cercano como parent
            allowClear: true
        });
    });
}

// Cuando cualquier modal se muestre, inicializamos Select2 solo en los selectores con ".select-class"
$('.modal').on('shown.bs.modal', function() {
    initializeSelect2(); // Llama a la función para inicializar Select2
});

function initializeSelect2ByClass(className) {
    $('.' + className).each(function() {
        $(this).select2({
            theme: 'bootstrap-5',
            allowClear: true // Mantiene la opción de permitir limpiar la selección
        });
    });
}
function initializeSelect2ByClassbtrap(className) {
    $('.' + className).each(function() {
        $(this).select2({
            theme: 'bootstrap-5',
            allowClear: true
        });
    });

}
function vista_previa_img(input_file, salida) {
    $(function() {
        $('#' + input_file).change(function(e) {
            if ($("#" + input_file).val() != "") {
                $('#' + salida).show();
                addImage(e);
            } else {
                $('#' + salida).hide();
                $('#' + salida).removeAttr('src');
            }
        });

        function addImage(e) {
            var file = e.target.files[0],
                imageType = /image.*/;

            if (!file.type.match(imageType))
                return;

            var reader = new FileReader();
            reader.onload = fileOnload;
            reader.readAsDataURL(file);
        }

        function fileOnload(e) {
            var result = e.target.result;
            $('#' + salida).attr("src", result);
        }
    });
}

function establecerFechaHoy(inputId) {
    const fechahoy = new Date(); // Fecha actual (const porque no se va a reasignar)
    let mes = fechahoy.getMonth() + 1; // Obteniendo mes (let porque puede cambiar)
    let dia = fechahoy.getDate(); // Obteniendo día (let porque puede cambiar)
    const anio = fechahoy.getFullYear(); // Obteniendo año (const porque no cambia)

    // Agregar ceros a día y mes si son menores de 10
    if (dia < 10) dia = '0' + dia;
    if (mes < 10) mes = '0' + mes;

    // Asignar el valor de la fecha al campo de entrada especificado
    document.getElementById(inputId).value = anio + "-" + mes + "-" + dia;
}

function actualizarFechaYHoraPorId(inputId) {
    var fechaActual = new Date();
    var dia = fechaActual.getDate();
    var mes = fechaActual.getMonth() + 1;
    var anio = fechaActual.getFullYear();
    var hora = fechaActual.getHours();
    var minutos = fechaActual.getMinutes();
    var segundos = fechaActual.getSeconds();

    // Asegurar que los valores sean de dos dígitos si son menores de 10
    if (dia < 10) dia = '0' + dia;
    if (mes < 10) mes = '0' + mes;
    if (hora < 10) hora = '0' + hora;
    if (minutos < 10) minutos = '0' + minutos;
    if (segundos < 10) segundos = '0' + segundos;

    var fechaHoraTexto = dia + "/" + mes + "/" + anio + " " + hora + ":" + minutos + ":" + segundos;

    // Asignar la fecha y hora al campo de texto con el ID proporcionado
    document.getElementById(inputId).textContent = "Fecha y Hora: " + fechaHoraTexto;
}
