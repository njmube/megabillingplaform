(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_transferedDeleteController',Tax_transferedDeleteController);

    Tax_transferedDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tax_transfered'];

    function Tax_transferedDeleteController($uibModalInstance, entity, Tax_transfered) {
        var vm = this;
        vm.tax_transfered = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Tax_transfered.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
