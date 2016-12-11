(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_public_notariesDeleteController',Freecom_public_notariesDeleteController);

    Freecom_public_notariesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_public_notaries'];

    function Freecom_public_notariesDeleteController($uibModalInstance, entity, Freecom_public_notaries) {
        var vm = this;
        vm.freecom_public_notaries = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_public_notaries.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
