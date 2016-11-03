(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_local_transferedDeleteController',Com_local_transferedDeleteController);

    Com_local_transferedDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_local_transfered'];

    function Com_local_transferedDeleteController($uibModalInstance, entity, Com_local_transfered) {
        var vm = this;
        vm.com_local_transfered = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_local_transfered.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
