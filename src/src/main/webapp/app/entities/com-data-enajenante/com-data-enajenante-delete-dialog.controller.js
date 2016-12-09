(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_data_enajenanteDeleteController',Com_data_enajenanteDeleteController);

    Com_data_enajenanteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_data_enajenante'];

    function Com_data_enajenanteDeleteController($uibModalInstance, entity, Com_data_enajenante) {
        var vm = this;
        vm.com_data_enajenante = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_data_enajenante.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
