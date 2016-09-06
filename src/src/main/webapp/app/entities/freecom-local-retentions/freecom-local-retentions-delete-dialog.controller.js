(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_local_retentionsDeleteController',Freecom_local_retentionsDeleteController);

    Freecom_local_retentionsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_local_retentions'];

    function Freecom_local_retentionsDeleteController($uibModalInstance, entity, Freecom_local_retentions) {
        var vm = this;

        vm.freecom_local_retentions = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_local_retentions.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
