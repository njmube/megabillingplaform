(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_public_notariesDeleteController',Com_public_notariesDeleteController);

    Com_public_notariesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_public_notaries'];

    function Com_public_notariesDeleteController($uibModalInstance, entity, Com_public_notaries) {
        var vm = this;
        vm.com_public_notaries = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_public_notaries.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
