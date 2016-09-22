(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_specific_descriptionsDetailController', Freecom_specific_descriptionsDetailController);

    Freecom_specific_descriptionsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_specific_descriptions', 'Freecom_commodity'];

    function Freecom_specific_descriptionsDetailController($scope, $rootScope, $stateParams, entity, Freecom_specific_descriptions, Freecom_commodity) {
        var vm = this;
        vm.freecom_specific_descriptions = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_specific_descriptionsUpdate', function(event, result) {
            vm.freecom_specific_descriptions = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
