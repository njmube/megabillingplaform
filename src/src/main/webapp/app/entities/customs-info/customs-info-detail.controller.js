(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Customs_infoDetailController', Customs_infoDetailController);

    Customs_infoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Customs_info', 'Concept', 'Part_concept'];

    function Customs_infoDetailController($scope, $rootScope, $stateParams, entity, Customs_info, Concept, Part_concept) {
        var vm = this;

        vm.customs_info = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:customs_infoUpdate', function(event, result) {
            vm.customs_info = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
