(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_typesDeleteController',Tax_typesDeleteController);

    Tax_typesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tax_types'];

    function Tax_typesDeleteController($uibModalInstance, entity, Tax_types) {
        var vm = this;

        vm.tax_types = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tax_types.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
