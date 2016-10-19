(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_request_confirmDeleteController',Taxpayer_request_confirmDeleteController);

    Taxpayer_request_confirmDeleteController.$inject = ['$uibModalInstance', 'entity', 'Taxpayer_request_confirm'];

    function Taxpayer_request_confirmDeleteController($uibModalInstance, entity, Taxpayer_request_confirm) {
        var vm = this;

        vm.taxpayer_request_confirm = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Taxpayer_request_confirm.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
