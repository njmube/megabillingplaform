(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_data_enajenanteDeleteController',Freecom_data_enajenanteDeleteController);

    Freecom_data_enajenanteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_data_enajenante'];

    function Freecom_data_enajenanteDeleteController($uibModalInstance, entity, Freecom_data_enajenante) {
        var vm = this;
        vm.freecom_data_enajenante = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_data_enajenante.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
