(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_retentions_transferedDeleteController',Freecom_retentions_transferedDeleteController);

    Freecom_retentions_transferedDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_retentions_transfered'];

    function Freecom_retentions_transferedDeleteController($uibModalInstance, entity, Freecom_retentions_transfered) {
        var vm = this;
        vm.freecom_retentions_transfered = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_retentions_transfered.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
