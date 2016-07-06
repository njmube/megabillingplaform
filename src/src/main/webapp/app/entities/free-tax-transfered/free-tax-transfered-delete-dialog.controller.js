(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_tax_transferedDeleteController',Free_tax_transferedDeleteController);

    Free_tax_transferedDeleteController.$inject = ['$uibModalInstance', 'entity', 'Free_tax_transfered'];

    function Free_tax_transferedDeleteController($uibModalInstance, entity, Free_tax_transfered) {
        var vm = this;
        vm.free_tax_transfered = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Free_tax_transfered.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
