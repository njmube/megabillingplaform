(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_addressDeleteController',Tax_addressDeleteController);

    Tax_addressDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tax_address'];

    function Tax_addressDeleteController($uibModalInstance, entity, Tax_address) {
        var vm = this;

        vm.tax_address = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tax_address.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
