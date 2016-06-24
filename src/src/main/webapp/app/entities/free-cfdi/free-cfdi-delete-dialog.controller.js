(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_cfdiDeleteController',Free_cfdiDeleteController);

    Free_cfdiDeleteController.$inject = ['$uibModalInstance', 'entity', 'Free_cfdi'];

    function Free_cfdiDeleteController($uibModalInstance, entity, Free_cfdi) {
        var vm = this;
        vm.free_cfdi = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Free_cfdi.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
