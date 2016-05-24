(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ServicesDeleteController',ServicesDeleteController);

    ServicesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Services'];

    function ServicesDeleteController($uibModalInstance, entity, Services) {
        var vm = this;
        vm.services = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Services.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
