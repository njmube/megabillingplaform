(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ConceptDetailController', ConceptDetailController);

    ConceptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Concept', 'Taxpayer_account', 'Measure_unit'];

    function ConceptDetailController($scope, $rootScope, $stateParams, entity, Concept, Taxpayer_account, Measure_unit) {
        var vm = this;

        vm.concept = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:conceptUpdate', function(event, result) {
            vm.concept = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
