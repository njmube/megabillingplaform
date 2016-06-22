(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Cfdi_typesDeleteController',Cfdi_typesDeleteController);

    Cfdi_typesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cfdi_types'];

    function Cfdi_typesDeleteController($uibModalInstance, entity, Cfdi_types) {
        var vm = this;

        vm.cfdi_types = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cfdi_types.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
