(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_specific_descriptionsDeleteController',Com_specific_descriptionsDeleteController);

    Com_specific_descriptionsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_specific_descriptions'];

    function Com_specific_descriptionsDeleteController($uibModalInstance, entity, Com_specific_descriptions) {
        var vm = this;
        vm.com_specific_descriptions = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_specific_descriptions.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
