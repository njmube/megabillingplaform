(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_tax_retentionsDeleteController',Free_tax_retentionsDeleteController);

    Free_tax_retentionsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Free_tax_retentions'];

    function Free_tax_retentionsDeleteController($uibModalInstance, entity, Free_tax_retentions) {
        var vm = this;
        vm.free_tax_retentions = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Free_tax_retentions.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
