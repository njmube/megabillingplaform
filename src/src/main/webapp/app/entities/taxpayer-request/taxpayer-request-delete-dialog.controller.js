(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_requestDeleteController',Taxpayer_requestDeleteController);

    Taxpayer_requestDeleteController.$inject = ['$uibModalInstance', 'entity', 'Taxpayer_request'];

    function Taxpayer_requestDeleteController($uibModalInstance, entity, Taxpayer_request) {
        var vm = this;

        vm.taxpayer_request = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Taxpayer_request.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
