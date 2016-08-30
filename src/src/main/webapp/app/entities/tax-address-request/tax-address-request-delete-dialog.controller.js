(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_address_requestDeleteController',Tax_address_requestDeleteController);

    Tax_address_requestDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tax_address_request'];

    function Tax_address_requestDeleteController($uibModalInstance, entity, Tax_address_request) {
        var vm = this;

        vm.tax_address_request = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tax_address_request.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
