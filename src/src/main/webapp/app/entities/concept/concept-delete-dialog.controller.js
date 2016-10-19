(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ConceptDeleteController',ConceptDeleteController);

    ConceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Concept'];

    function ConceptDeleteController($uibModalInstance, entity, Concept) {
        var vm = this;
        vm.concept = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Concept.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
