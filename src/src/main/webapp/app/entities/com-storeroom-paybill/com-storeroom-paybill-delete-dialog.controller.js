(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_storeroom_paybillDeleteController',Com_storeroom_paybillDeleteController);

    Com_storeroom_paybillDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_storeroom_paybill'];

    function Com_storeroom_paybillDeleteController($uibModalInstance, entity, Com_storeroom_paybill) {
        var vm = this;
        vm.com_storeroom_paybill = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_storeroom_paybill.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
