(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_storeroom_paybillDeleteController',Freecom_storeroom_paybillDeleteController);

    Freecom_storeroom_paybillDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_storeroom_paybill'];

    function Freecom_storeroom_paybillDeleteController($uibModalInstance, entity, Freecom_storeroom_paybill) {
        var vm = this;
        vm.freecom_storeroom_paybill = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_storeroom_paybill.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
