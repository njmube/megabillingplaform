(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ConceptDetailController', ConceptDetailController);

    ConceptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Concept', 'Measure_unit', 'Cfdi'];

    function ConceptDetailController($scope, $rootScope, $stateParams, entity, Concept, Measure_unit, Cfdi) {
        var vm = this;
        vm.concept = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:conceptUpdate', function(event, result) {
            vm.concept = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
