(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_info_customs_destructionDeleteController',Freecom_info_customs_destructionDeleteController);

    Freecom_info_customs_destructionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_info_customs_destruction'];

    function Freecom_info_customs_destructionDeleteController($uibModalInstance, entity, Freecom_info_customs_destruction) {
        var vm = this;
        vm.freecom_info_customs_destruction = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_info_customs_destruction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
